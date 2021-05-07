package com.skcc.accountbank.subscribe;

import com.skcc.modern.pattern.message.util.Message;
import com.skcc.modern.pattern.message.util.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skcc.account.event.message.AccountEvent;
import com.skcc.accountbank.service.AccountBankService;

@Component
public class AccountBankSubscribe {

	private AccountBankService accountBankService;
	
	@Autowired
	public AccountBankSubscribe(AccountBankService accountBankService) {
		this.accountBankService = accountBankService;
	}
	
	@MessageListener(topics = {"AccountCreated.account"})
	public void createAccountBank(Message message) {
		AccountEvent accountEvent = message.getPayloadAsType(AccountEvent.class);
		this.accountBankService.createAccountBankAndCreatePublishEvent(accountEvent);
	}
	
}
