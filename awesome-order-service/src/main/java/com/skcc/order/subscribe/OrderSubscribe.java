package com.skcc.order.subscribe;

import com.skcc.modern.pattern.message.util.Message;
import com.skcc.modern.pattern.message.util.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skcc.order.service.OrderService;
import com.skcc.payment.event.message.PaymentEvent;
import com.skcc.product.event.message.ProductEvent;

@Component
public class OrderSubscribe {

	private OrderService orderService;

	@Autowired
	public OrderSubscribe(OrderService orderService) {
		this.orderService = orderService;
	}

	@MessageListener(topics = {"ProductAmountSubtractFailed.product"})
	public void receiveProductAmountSubtractFailedEvent(Message message) {
		ProductEvent productEvent = message.getPayloadAsType(ProductEvent.class);
		this.orderService.cancelOrderAndCreatePublishOrderEvent(productEvent);
	}

	@MessageListener(topics = {"PaymentCreateFailed.payment"})
	public void receivePaymentCreateFailedEvent(Message message) {
		PaymentEvent paymentEvent = message.getPayloadAsType(PaymentEvent.class);
		this.orderService.cancelOrderAndCreatePublishOrderEvent(paymentEvent);
	}

	@MessageListener(topics = {"PaymentPaid.payment"})
	public void receivePaymentPaidEvent(Message message) {
		PaymentEvent paymentEvent = message.getPayloadAsType(PaymentEvent.class);
		this.orderService.payOrderAndCreatePublishOrderEvent(paymentEvent);
	}

	@MessageListener(topics = {"PaymentCreated.payment"})
	public void receivePaymentCreatedEvent(Message message) {
		PaymentEvent paymentEvent = message.getPayloadAsType(PaymentEvent.class);
		this.orderService.setOrderPaymentIdAndCreatePublishOrderEvent(paymentEvent);
	}
}
