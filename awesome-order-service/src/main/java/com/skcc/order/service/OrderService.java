package com.skcc.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.skcc.modern.pattern.message.producer.ProduceService;
import com.skcc.modern.pattern.message.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skcc.order.domain.Order;
import com.skcc.order.domain.OrderPayment;
import com.skcc.order.event.message.OrderEvent;
import com.skcc.order.event.message.OrderEventType;
import com.skcc.order.event.message.OrderPayload;
import com.skcc.order.repository.OrderMapper;
import com.skcc.payment.event.message.PaymentEvent;
import com.skcc.product.event.message.ProductEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class OrderService {
	
	private OrderMapper orderMapper;
	private ProduceService produceService;

	@Value("${domain.name}")
	private String domain;
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	public OrderService(OrderMapper orderMapper, ProduceService produceService) {
		this.orderMapper = orderMapper;
		this.produceService = produceService;
	}
	
	public List<Order> findOrderByAccountId(long accountId) {
		return this.orderMapper.findOrderByAccountId(accountId);
	}
	
	public boolean createOrderAndCreatePublishOrderEvent(Order order) {
		boolean result = false;
		
		try {
			this.createOrderAndCreatePublishOrderCreatedEvent(order);
			result = true;
		} catch(Exception e) {
			try {
				e.printStackTrace();
				this.createPublishOrderCreateFailedEvent(order);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean cancelOrderAndCreatePublishOrderEvent(long id) {
		boolean result = false;
		
		Order resultOrder = this.findOrderById(id);
		try {
			this.cancelOrderAndCreatePublishOrderCanceledEvent(null, resultOrder);
			result = true;
		} catch(Exception e) {
			try {
				e.printStackTrace();
				this.createPublishOrderCanceledEvent(null, resultOrder);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void cancelOrderAndCreatePublishOrderEvent(ProductEvent productEvent) {
		String txId = productEvent.getTxId();
		String eventType = OrderEventType.OrderCreated.toString();
		Order resultOrder = this.convertOrderEventToOrder(this.findOrderEventByTxId(txId, eventType));
		try {
			this.cancelOrderAndCreatePublishOrderCanceledEvent(txId, resultOrder);
		} catch(Exception e) {
			try {
				e.printStackTrace();
				this.createPublishOrderCanceledEvent(txId, resultOrder);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public void cancelOrderAndCreatePublishOrderEvent(PaymentEvent paymentEvent) {
		String txId = paymentEvent.getTxId();
		String eventType = OrderEventType.OrderCreated.toString();
		Order resultOrder = this.convertOrderEventToOrder(this.findOrderEventByTxId(txId, eventType));
		try {
			this.cancelOrderAndCreatePublishOrderCanceledEvent(txId, resultOrder);
		} catch(Exception e) {
			try {
				e.printStackTrace();
				this.createPublishOrderCanceledEvent(txId, resultOrder);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public void setOrderPaymentIdAndCreatePublishOrderEvent(PaymentEvent paymentEvent) {

		Order resultOrder = this.findOrderAndSetPaymentId(paymentEvent);
		try {
			this.setOrderPaymentIdAndCreatePublishOrderPaymentIdSetEvent(paymentEvent.getTxId(), resultOrder);
		} catch(Exception e) {
			try {
				e.printStackTrace();
				this.createPublishOrderPaymentIdSetFailedEvent(paymentEvent.getTxId(), resultOrder);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void payOrderAndCreatePublishOrderEvent(PaymentEvent paymentEvent) {
		String txId = paymentEvent.getTxId();
		Order resultOrder = this.findOrderAndSetPaid(paymentEvent);
		try {
			this.payOrderAndCreatePublishOrderPaidEvent(txId, resultOrder);
		} catch(Exception e) {
			try {
				e.printStackTrace();
				this.createPublishOrderPayFailedEvent(txId, resultOrder);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Transactional
	public void createOrderAndCreatePublishOrderCreatedEvent(Order order) {
		Order resultOrder = this.createOrder(order);
		this.CreatePublishOrderEvent(null, resultOrder, OrderEventType.OrderCreated);
	}
	
	@Transactional
	public void createPublishOrderCreateFailedEvent(Order order) {
		this.CreatePublishOrderEvent(null, order, OrderEventType.OrderCreateFailed);
	}
	
	@Transactional
	public void cancelOrderAndCreatePublishOrderCanceledEvent(String txId, Order order) throws Exception {
		this.cancelOrder(order);
		this.CreatePublishOrderEvent(txId, order, OrderEventType.OrderCanceled);
	}
	
	@Transactional
	public void createPublishOrderCanceledEvent(String txId, Order order) {
		this.CreatePublishOrderEvent(txId, order, OrderEventType.OrderCancelFailed);
	}
	
	@Transactional
	public void setOrderPaymentIdAndCreatePublishOrderPaymentIdSetEvent(String txId, Order order) throws Exception {
		this.setOrderPaymentId(order);
		this.CreatePublishOrderEvent(txId, order, OrderEventType.OrderPaymentIdSet);
	}
	
	@Transactional
	public void createPublishOrderPaymentIdSetFailedEvent(String txId, Order order) {
		this.CreatePublishOrderEvent(txId, order, OrderEventType.OrderPaymentIdSetFailed);
	}
	
	@Transactional
	public void payOrderAndCreatePublishOrderPaidEvent(String txId, Order order) throws Exception {
		this.payOrder(order);
		this.CreatePublishOrderEvent(txId, order, OrderEventType.OrderPaid);
	}
	
	@Transactional
	public void createPublishOrderPayFailedEvent(String txId, Order order) {
		this.CreatePublishOrderEvent(txId, order, OrderEventType.OrderPayFailed);
	}

	private Order createOrder(Order order) {
		long orderId = this.orderMapper.getOrderId();
		order.setId(orderId);
		order.getPaymentInfo().setOrderId(orderId);
		order.setPaid("unpaid");
		order.setStatus("ordered");
		this.orderMapper.createOrder(order);
		
		return order;
	}
	
	private void cancelOrder(Order order) throws Exception {
		this.cancelOrderValidationCheck(order);
		order.setStatus("canceled");
		this.orderMapper.cancelOrder(order.getId());
	}
	
	private void cancelOrderValidationCheck(Order order) throws Exception{
		if(order.getStatus().isEmpty() || !order.getStatus().equals("ordered"))
			throw new Exception();
	}
	
	private void setOrderPaymentId(Order order) throws Exception {
		this.setOrderPaymentIdValidationCheck(order);
		this.orderMapper.setOrderPaymentId(order);
	}
	
	private void payOrder(Order order) throws Exception {
		this.payOrderValidationCheck(order);
		this.orderMapper.payOrder(order);
	}
	
	private void payOrderValidationCheck(Order order) throws Exception {
		//test용
//		throw new Exception();
		
		if(!"paid".equals(order.getPaid()))
			throw new Exception();
	}
	
	private Order findOrderAndSetPaymentId(PaymentEvent paymentEvent) {
		Order order = this.findOrderById(paymentEvent.getPayload().getOrderId());
		order.setPaymentId(paymentEvent.getPayload().getId());
		order.setPaymentInfo(this.convertPaymentEventToOrderPayment(paymentEvent));
		
		return order;
	}
	
	private Order findOrderAndSetPaid(PaymentEvent paymentEvent) {
		Order order = this.findOrderById(paymentEvent.getPayload().getOrderId());
		order.setPaid(paymentEvent.getPayload().getPaid());
		//Order is completed because there are no processes after payments
		order.setStatus("completed");
		order.getPaymentInfo().setPaid(paymentEvent.getPayload().getPaid());
		return order;
	}
	
	private OrderPayment convertPaymentEventToOrderPayment(PaymentEvent paymentEvent) {
		OrderPayment orderPayment = new OrderPayment();
		
		orderPayment.setId(paymentEvent.getPayload().getId());
		orderPayment.setAccountId(paymentEvent.getPayload().getAccountId());
		orderPayment.setOrderId(paymentEvent.getPayload().getOrderId());
		orderPayment.setPaymentMethod(paymentEvent.getPayload().getPaymentMethod());
		orderPayment.setPaymentDetail1(paymentEvent.getPayload().getPaymentDetail1());
		orderPayment.setPaymentDetail2(paymentEvent.getPayload().getPaymentDetail2());
		orderPayment.setPaymentDetail3(paymentEvent.getPayload().getPaymentDetail3());
		orderPayment.setPrice(paymentEvent.getPayload().getPrice());
		orderPayment.setPaid(paymentEvent.getPayload().getPaid());
		orderPayment.setActive(paymentEvent.getPayload().getActive());
		
		return orderPayment;
	}
	
	private void setOrderPaymentIdValidationCheck(Order order) throws Exception {
		if(order.getPaymentId() == 0)
			throw new Exception();
	}
	
	public List<OrderEvent> getOrderEvent(){
		return this.orderMapper.getOrderEvent();
	}
	
	private void CreatePublishOrderEvent(String txId, Order order, OrderEventType orderEventType) {
		OrderEvent orderEvent = this.convertOrderToOrderEvent(txId, order.getId(), orderEventType);
		this.createOrderEvent(orderEvent);
		this.publishOrderEvent(orderEvent);
	}
	
	private void publishOrderEvent(OrderEvent orderEvent) {
		// Topic 설정
		String topic = orderEvent.getEventType().name() + "." + domain;
		// Message 발행
		produceService.publish(topic, orderEvent);
	}
	
	private void createOrderEvent(OrderEvent orderEvent) {
		this.orderMapper.createOrderEvent(orderEvent);
	}
	
	private OrderEvent convertOrderToOrderEvent(String txId, long id, OrderEventType orderEventType) {
		log.info("in service txId : {}", txId);

		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			txId = attr.getRequest().getHeader("X-TXID");
		}

		Order order = this.findOrderById(id);
		
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setId(this.getOrderEventId());
		orderEvent.setDomain(domain);
		orderEvent.setOrderId(order.getId());
		orderEvent.setEventType(orderEventType);
		orderEvent.setPayload(new OrderPayload(order.getId(), order.getAccountId(), order.getPaymentId(), order.getAccountInfo(), order.getPaymentInfo(), order.getProductsInfo(), order.getPaid(), order.getStatus()));
		orderEvent.setTxId(txId);
		orderEvent.setCreatedAt(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		log.info("in service orderEvent : {}", orderEvent.toString());
		
		return orderEvent;
	}
	
	private Order convertOrderEventToOrder(OrderEvent orderEvent) {
		Order order = new Order();
		
		order.setId(orderEvent.getOrderId());
		order.setAccountId(orderEvent.getPayload().getAccountId());
		order.setAccountInfo(orderEvent.getPayload().getAccountInfo());
		order.setPaymentId(orderEvent.getPayload().getPaymentId());
		order.setPaymentInfo(orderEvent.getPayload().getPaymentInfo());
		order.setProductsInfo(orderEvent.getPayload().getProductsInfo());
		order.setPaid(orderEvent.getPayload().getPaid());
		order.setStatus(orderEvent.getPayload().getStatus());
		
		return order;
	}
	
	private long getOrderEventId() {
		return this.orderMapper.getOrderEventId();
	}
	
	private Order findOrderById(long id) {
		return this.orderMapper.findOrderById(id);
	}
	
	private OrderEvent findOrderEventByTxId(String txId, String eventType) {
		return this.orderMapper.findOrderEventByTxId(txId, eventType);
	}
	
}