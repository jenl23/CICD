package com.skcc.accountbank.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skcc.accountbank.event.message.AccountBankEventType;

@Aspect
@Component
public class ConvertAccountBankEventAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ConvertAccountBankEventAspect.class);

	@Pointcut("execution(* com.skcc.*.service.*.convertAccountBankToAccountBankEvent(..))")
	public void convertAccountBankToAccountBankEvent() {}
	
	@Around("convertAccountBankToAccountBankEvent() && args(txId, id, AccountBankEventType)")
	public Object setTxId(ProceedingJoinPoint pjp, String txId, long id, AccountBankEventType AccountBankEventType) throws Throwable {
		//request에 의한 호출시 txId == null
		//subsribe에 의한 호출시 txId != null
		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			txId = attr.getRequest().getHeader("X-TXID");
		}
		
		return pjp.proceed(new Object[] {txId, id, AccountBankEventType});
	}
}
