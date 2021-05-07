package com.skcc.accountbank.service;

import java.util.concurrent.ThreadLocalRandom;

import com.skcc.modern.pattern.message.producer.ProduceService;
import com.skcc.modern.pattern.message.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skcc.account.event.message.AccountEvent;
import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.event.message.AccountBankEvent;
import com.skcc.accountbank.event.message.AccountBankEventType;
import com.skcc.accountbank.event.message.AccountBankPayload;
import com.skcc.accountbank.repository.AccountBankMapper;

@Service
public class AccountBankService {
	
	private static final Logger log = LoggerFactory.getLogger(AccountBankService.class);

	private AccountBankMapper accountBankMapper;
	private ProduceService produceService;
	
	@Value("${domain.name}")
	private String domain;
	
	@Autowired
	private AccountBankService accountBankService;
	
	@Autowired
	public AccountBankService(AccountBankMapper accountBankMapper, ProduceService produceService) {
		this.accountBankMapper = accountBankMapper;
		this.produceService = produceService;
	}
	
	public AccountBank findAccountBankByAccountId(String txId, long accountId) {
		return accountBankMapper.findAccountBankByAccountId(accountId);
	}
	
	public boolean createAccountBankAndCreatePublishEvent(AccountEvent accountEvent) {
		boolean result = false;
		AccountBank accountBank = this.convertAccountEventToAccountBank(accountEvent);
		try {
			this.createAccountBankAndCreatePublishAccountBankCreatedEvent(accountEvent.getTxId(), accountBank);
			result = true;
		} catch(Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.createPublishAccountBankCreateFailedEvent(accountEvent.getTxId(), accountBank);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	@Transactional
	public void createAccountBankAndCreatePublishAccountBankCreatedEvent(String txId, AccountBank accountBank) throws Exception {
		AccountBank resultAccountBank = this.createAccountBank(accountBank);
		this.createPublishAccountBankEvent(txId, resultAccountBank, AccountBankEventType.AccountBankCreated);
	}
	
	@Transactional
	public void createPublishAccountBankCreateFailedEvent(String txId, AccountBank accountBank) {
		this.createPublishAccountBankEvent(txId, accountBank, AccountBankEventType.AccountBankCreateFailed);
	}
	
	public AccountBank createAccountBank(AccountBank accountBank) throws Exception {
		this.createAccountBankValidationCheck(accountBank);
		this.accountBankMapper.createAccountBank(accountBank);
		
		return accountBank;
	}
	
	public void createPublishAccountBankEvent(String txId, AccountBank accountBank, AccountBankEventType accountBankEventType) {
		AccountBankEvent accountBankEvent = this.accountBankService.convertAccountBankToAccountBankEvent(txId, accountBank.getId(), accountBankEventType);
		this.createAccountBankEvent(accountBankEvent);
		this.publishAccountBankEvent(accountBankEvent);
	}
	
	public void createAccountBankValidationCheck(AccountBank accountBank) throws Exception{
//		if(this.accountBankMapper.findAccountBankByAccountId(accountBank.getAccountId()) != null)
//			throw new Exception();
	}
	
	public AccountBank convertAccountEventToAccountBank(AccountEvent accountEvent) {
		log.info(accountEvent.toString());
		
		AccountBank accountBank = new AccountBank();
		accountBank.setAccountId(accountEvent.getAccountId());
		accountBank.setActive("active");
		accountBank.setBankName("kb");
		accountBank.setBankNumber(this.generateBankNumber());
		
		return accountBank;
	}
	
	public String generateBankNumber() {
		long result = ThreadLocalRandom.current().nextLong();
		if(result < 0) 
			result = result * (-1);
		
		return (String) String.valueOf(result).subSequence(0, 14);
	}
	
	public void createAccountBankEvent(AccountBankEvent accountBankEvent) {
		this.accountBankMapper.createAccountBankEvent(accountBankEvent);
	}
	
	public void publishAccountBankEvent(AccountBankEvent accountBankEvent) {
		produceService.publish(accountBankEvent.getEventType().name() + "." + domain, accountBankEvent);
	}
	
	public AccountBankEvent convertAccountBankToAccountBankEvent(String txId, long id, AccountBankEventType accountBankEventType) {
		log.info("in service txId : " + txId);
		
		AccountBank accountBank = this.findById(id);
		
		AccountBankEvent accountBankEvent = new AccountBankEvent();
		accountBankEvent.setId(this.accountBankMapper.getAccountBankEventId());
		accountBankEvent.setAccountBankId(accountBank.getId());
		accountBankEvent.setDomain(domain);
		accountBankEvent.setEventType(accountBankEventType); 
		accountBankEvent.setPayload(new AccountBankPayload(accountBank.getId(), accountBank.getAccountId(), accountBank.getBankName(), accountBank.getBankNumber(), accountBank.getActive()));
		accountBankEvent.setTxId(txId);
		
		log.info("in service accountBankEvent : " + accountBankEvent.toString());
		
		return accountBankEvent;
	}
	
	public AccountBank findById(long id) {
		return this.accountBankMapper.findById(id); 
	}
	
}
