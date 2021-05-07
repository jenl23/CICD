package com.skcc.payment.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.payment.domain.Payment;
import com.skcc.payment.event.message.PaymentEvent;
import com.skcc.payment.service.PaymentService;

@RestController
@RequestMapping("/v1")
public class PaymentController {
	
	private PaymentService paymentService;
	
	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping(value="/payments")
	public List<Payment> findPaymentByAccountID(@PathParam(value="accountId") long accountId) {
		return paymentService.findPaymentByAccountId(accountId);
	}
	
	@GetMapping(value="/payments/events")
	public List<PaymentEvent> getPaymentEvent(){
		return this.paymentService.getPaymentEvent();
	}
}
