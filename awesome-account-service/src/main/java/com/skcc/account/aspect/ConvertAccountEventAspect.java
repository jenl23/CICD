package com.skcc.account.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skcc.account.event.message.AccountEventType;

@Aspect
@Component
public class ConvertAccountEventAspect {
		
	private static final Logger log = LoggerFactory.getLogger(ConvertAccountEventAspect.class);

	@Pointcut("execution(* com.skcc.*.service.*.convertAccountToAccountEvent(..))")
	public void convertAccountToAccountEvent() {}
	
	@Around("convertAccountToAccountEvent() && args(txId, id, accountEventType)")
	public Object setTxId(ProceedingJoinPoint pjp, String txId, long id, AccountEventType accountEventType) throws Throwable {
		//request에 의한 호출시 txId == null
		//subsribe에 의한 호출시 txId != null
		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			txId = attr.getRequest().getHeader("X-TXID");
		}
		
		return pjp.proceed(new Object[] {txId, id, accountEventType});
	}
	
}
