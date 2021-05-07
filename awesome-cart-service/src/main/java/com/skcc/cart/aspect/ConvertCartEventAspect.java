package com.skcc.cart.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skcc.cart.domain.Cart;
import com.skcc.cart.event.message.CartEventType;

@Aspect
@Component
public class ConvertCartEventAspect {
		
	private static final Logger log = LoggerFactory.getLogger(ConvertCartEventAspect.class);

	@Pointcut("execution(* com.skcc.*.service.*.convertCartToCartEvent(..))")
	public void convertCartToCartEvent() {}
	
	@Around("convertCartToCartEvent() && args(txId, cart, cartEventType)")
	public Object setTxId(ProceedingJoinPoint pjp, String txId, Cart cart, CartEventType cartEventType) throws Throwable {
		//request에 의한 호출시 txId == null
		//subsribe에 의한 호출시 txId != null
		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			txId = attr.getRequest().getHeader("X-TXID");
		}
		
		return pjp.proceed(new Object[] {txId, cart, cartEventType});
	}
	
}
