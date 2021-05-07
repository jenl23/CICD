package com.skcc.payment.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.skcc.payment.domain.Payment;
import com.skcc.payment.event.message.PaymentEvent;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PaymentMapper {

	List<Payment> findPaymentByAccountId(long accountId);
	
	Payment findPaymentByOrderId(long orderId);
	
	Payment findById(long id);
	
	void createPayment(Payment payment);
	
	void setPaymentPaid(@Param("paid") String paid, @Param("id") long id);
	
	Payment findunPaidPaymentById(long id);
	
	void cancelPayment(Payment payment);
	
	PaymentEvent findPreviousPaymentEvent(@Param("txId") String txId, @Param("paymentId") long paymentId);
	
	List<PaymentEvent> getPaymentEvent();
	
	long getPaymentEventId();
	
	void createPaymentEvent(PaymentEvent paymentEvent);

}
