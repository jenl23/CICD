package com.skcc.account.repository;

import java.util.List;

import com.skcc.account.event.message.AccountEvent;
import org.apache.ibatis.annotations.Mapper;

import com.skcc.account.domain.Account;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountMapper {
	
	Account findByUsername(String username);
	
	Account findById(long id);
	
	void createAccount(Account account);
	
	boolean editAccount(Account account);
	
	long getAccountEventId();
	
	boolean createAccountEvent(AccountEvent accountEvent);
	
	List<AccountEvent> getAccountEvent();
	
}
