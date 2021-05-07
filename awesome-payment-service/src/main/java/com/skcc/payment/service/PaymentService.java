package com.skcc.payment.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.skcc.modern.pattern.message.producer.ProduceService;
import com.skcc.modern.pattern.message.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skcc.order.event.message.OrderEvent;
import com.skcc.payment.domain.Payment;
import com.skcc.payment.event.message.PaymentEvent;
import com.skcc.payment.event.message.PaymentEventType;
import com.skcc.payment.event.message.PaymentPayload;
import com.skcc.payment.repository.PaymentMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class PaymentService {

	private PaymentMapper paymentMapper;
	private ProduceService produceService;

	@Value("${domain.name}")
	String domain;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	
	@Autowired
	public PaymentService(PaymentMapper paymentMapper, ProduceService produceService) {
		this.paymentMapper = paymentMapper;
		this.produceService = produceService;
	}
	
	public boolean createPaymentAndCreatePublishEvent(OrderEvent orderEvent) {
        boolean result = false;
        Payment payment = this.convertOrderEventToPayment(orderEvent);
        payment.setPaid("unpaid");
        try {
            this.createPaymentAndCreatePublishPaymentCreatedEvent(orderEvent.getTxId(), payment);
            result = true;
        } catch(Exception e) {
            try {
                result = false;
                e.printStackTrace();
                this.createPublishPaymentCreateFailedEvent(orderEvent.getTxId(), payment);
            }catch(Exception e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }
	
    @Transactional
    public void createPaymentAndCreatePublishPaymentCreatedEvent(String txId, Payment payment) throws Exception {
        this.createPaymentValidationCheck(payment);
        Payment resultPayment = this.createPayment(payment);
        this.createPublishPaymentEvent(txId, resultPayment, PaymentEventType.PaymentCreated);
    }
	
    @Transactional
    public void createPublishPaymentCreateFailedEvent(String txId, Payment payment) throws Exception{
        this.createPublishPaymentEvent(txId, payment, PaymentEventType.PaymentCreateFailed);
    }

    public boolean cancelPaymentAndCreatePublishEvent(OrderEvent orderEvent) {
        boolean result = false;
        String txId = orderEvent.getTxId();
        Payment payment = this.findPaymentByOrderId(orderEvent.getOrderId());
        if(payment == null)
            return result;
        try {
            this.cancelPaymentAndCreatePublishPaymentCanceledEvent(txId, payment);
            result = true;
        } catch(Exception e) {
            try {
                result = false;
                e.printStackTrace();
                this.createPublishPaymentCancelFailedEvent(txId, payment);
            }catch(Exception e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }
	
    @Transactional
    public void cancelPaymentAndCreatePublishPaymentCanceledEvent(String txId, Payment payment) throws Exception{
        this.cancelPaymentValidationCheck(payment);
        this.cancelPayment(payment);
        this.createPublishPaymentEvent(txId, payment, PaymentEventType.PaymentCanceled);
    }
	
    @Transactional
    public void createPublishPaymentCancelFailedEvent(String txId, Payment payment) {
        this.createPublishPaymentEvent(txId, payment, PaymentEventType.PaymentCancelFailed);
    }
	
	public void cancelPayment(Payment payment) {
		this.paymentMapper.cancelPayment(payment);
	}
	
	public void cancelPaymentValidationCheck(Payment payment) throws Exception {
		if(payment == null)
			throw new Exception();
		if("paid".equals(payment.getPaid()))
			throw new Exception();
	}

	public Payment createPayment(Payment payment) {
		this.paymentMapper.createPayment(payment);
		return payment;
	}
	
	public void createPublishPaymentEvent(String txId, Payment payment, PaymentEventType paymentEventType) {
		PaymentEvent paymentEvent = this.convertPaymentToPaymentEvent(txId, payment, paymentEventType);
		this.createPaymentEvent(paymentEvent);
		this.publishPaymentEvent(paymentEvent);
	}
	
	public void createPaymentEvent(PaymentEvent paymentevent) {
		this.paymentMapper.createPaymentEvent(paymentevent);
	}
	
	public void publishPaymentEvent(PaymentEvent paymentEvent) {
		produceService.publish(paymentEvent.getEventType().name() + "." + domain, paymentEvent);
	}
	
	public Payment findById(long id) {
		return this.paymentMapper.findById(id);
	}
	
	public long getPaymentEventId() {
		return this.paymentMapper.getPaymentEventId();
	}
	
	public List<Payment> findPaymentByAccountId(long accountId) {
		return this.paymentMapper.findPaymentByAccountId(accountId); 
	}
	
	public Payment convertOrderEventToPayment(OrderEvent orderEvent) {
		Payment payment = new Payment();
		
		payment.setId(orderEvent.getPayload().getPaymentId());
		payment.setAccountId(orderEvent.getPayload().getPaymentInfo().getAccountId());
		payment.setOrderId(orderEvent.getPayload().getPaymentInfo().getOrderId());
		payment.setPaymentMethod(orderEvent.getPayload().getPaymentInfo().getPaymentMethod());
		payment.setPaymentDetail1(orderEvent.getPayload().getPaymentInfo().getPaymentDetail1());
		payment.setPaymentDetail2(orderEvent.getPayload().getPaymentInfo().getPaymentDetail2());
		payment.setPaymentDetail3(orderEvent.getPayload().getPaymentInfo().getPaymentDetail3());
		payment.setPrice(orderEvent.getPayload().getPaymentInfo().getPrice());
		payment.setPaid(orderEvent.getPayload().getPaymentInfo().getPaid());
		payment.setActive(orderEvent.getPayload().getPaymentInfo().getActive());
		
		return payment;
	}
	
	public Payment convertPaymentEventToPayment(PaymentEvent paymentEvent) {
		Payment payment = new Payment();
		
		payment.setId(paymentEvent.getPayload().getId());
		payment.setAccountId(paymentEvent.getPayload().getAccountId());
		payment.setOrderId(paymentEvent.getPayload().getOrderId());
		payment.setPaymentMethod(paymentEvent.getPayload().getPaymentMethod());
		payment.setPaymentDetail1(paymentEvent.getPayload().getPaymentDetail1());
		payment.setPaymentDetail2(paymentEvent.getPayload().getPaymentDetail2());
		payment.setPaymentDetail3(paymentEvent.getPayload().getPaymentDetail3());
		payment.setPrice(paymentEvent.getPayload().getPrice());
		payment.setPaid(paymentEvent.getPayload().getPaid());
		payment.setActive(paymentEvent.getPayload().getActive());
		
		return payment;
	}
	
	public PaymentEvent convertPaymentToPaymentEvent(String txId, Payment payment, PaymentEventType paymentEventType) {
		log.info("in service txId : {}", txId);

		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			txId = attr.getRequest().getHeader("X-TXID");
		}

		long id = payment.getId();
		
		if(id != 0)
			payment = this.findById(id);
		
		PaymentEvent paymentEvent = new PaymentEvent();
		paymentEvent.setId(this.getPaymentEventId());
		paymentEvent.setPaymentId(id);
		paymentEvent.setDomain(domain);
		paymentEvent.setEventType(paymentEventType);
		paymentEvent.setPayload(new PaymentPayload(payment.getId(), payment.getAccountId(), payment.getOrderId(), payment.getPaymentMethod(), payment.getPaymentDetail1(), payment.getPaymentDetail2(), payment.getPaymentDetail3(), payment.getPrice(), payment.getPaid(), payment.getActive()));
		paymentEvent.setTxId(txId);
		paymentEvent.setCreatedAt(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		log.info("in service paymentEvent : {}", paymentEvent.toString());
		
		return paymentEvent;
	}
	
	public void createPaymentValidationCheck(Payment payment) throws Exception {
		if(payment.getPrice() == 0)
			throw new Exception();
		if(payment.getPaymentMethod().isEmpty())
			throw new Exception();
		if(payment.getAccountId() == 0)
			throw new Exception();
	}
	
	public Payment findUnpaidPaymentById(long id) {
		return this.paymentMapper.findunPaidPaymentById(id);
	}
	
	public Payment findPaymentByOrderId(long orderId) {
		return this.paymentMapper.findPaymentByOrderId(orderId);
	}
	
	public PaymentEvent findPreviousPaymentEvent(String txId, long paymentId) {
		return this.paymentMapper.findPreviousPaymentEvent(txId, paymentId);
	}
	
	public List<PaymentEvent> getPaymentEvent() {
		return this.paymentMapper.getPaymentEvent();
	}
	
}
