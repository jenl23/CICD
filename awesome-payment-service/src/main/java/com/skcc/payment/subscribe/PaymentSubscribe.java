package com.skcc.payment.subscribe;

import com.skcc.modern.pattern.message.util.Message;
import com.skcc.modern.pattern.message.util.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skcc.order.event.message.OrderEvent;
import com.skcc.payment.service.PaymentService;

@Component
public class PaymentSubscribe {

	private PaymentService paymentService;
	
	@Autowired
	public PaymentSubscribe(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@MessageListener(topics = {"OrderCreated.order"})
	public void receiveOrderCreatedEvent(Message message) {
		OrderEvent orderEvent = message.getPayloadAsType(OrderEvent.class);
		this.paymentService.createPaymentAndCreatePublishEvent(orderEvent);
	}

	@MessageListener(topics = {"OrderCanceled.order"})
	public void receiveOrderCanceledEvent(Message message) {
		OrderEvent orderEvent = message.getPayloadAsType(OrderEvent.class);
		this.paymentService.cancelPaymentAndCreatePublishEvent(orderEvent);
	}
}
