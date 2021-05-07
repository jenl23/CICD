package com.skcc.payment.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skcc.payment.domain.Payment;
import com.skcc.payment.event.message.PaymentEventType;

@Aspect
@Component
public class ConvertPaymentEventAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ConvertPaymentEventAspect.class);

	@Pointcut("execution(* com.skcc.*.service.*.convertPaymentToPaymentEvent(..))")
	public void convertPaymentToPaymentEvent() {}
	
	@Around("convertPaymentToPaymentEvent() && args(txId, payment, paymentEventType)")
	public Object setTxId(ProceedingJoinPoint pjp, String txId, Payment payment, PaymentEventType paymentEventType) throws Throwable {
		//request에 의한 호출시 txId == null
		//subsribe에 의한 호출시 txId != null
		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			txId = attr.getRequest().getHeader("X-TXID");
		}
		
		return pjp.proceed(new Object[] {txId, payment, paymentEventType});
	}
}
