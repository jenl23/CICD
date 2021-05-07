package com.skcc.accountbank.repository;

import org.apache.ibatis.annotations.Mapper;

import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.event.message.AccountBankEvent;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountBankMapper {
	
	AccountBank findAccountBankByAccountId(long accountId);

	AccountBank findById(long id);
	 
	long getAccountBankEventId();
	
	void createAccountBankEvent(AccountBankEvent accountBankEvent);
	
	void createAccountBank(AccountBank accountBank);
	
}
