package com.skcc.account.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.account.domain.Account;
import com.skcc.account.event.message.AccountEvent;
import com.skcc.account.service.AccountService;

@RestController
@RequestMapping("/v1")
public class AccountController {
	
	private AccountService accountService;
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	public AccountController(AccountService accountService){
		this.accountService = accountService;
	}
	
	@PostMapping(value="/login")
	public Account login(@RequestBody Account account) {
		return accountService.login(account);
	}
	
	@GetMapping(value="/accounts/{id}")
	public Account findById(@PathVariable long id) {
		return this.accountService.findById(id);
	}
	
	@PostMapping(value="/accounts")
	public boolean createAccount(@RequestBody Account account) throws Exception {
		return this.accountService.createAccountAndCreatePublishEvent(account);
	}
	
	@GetMapping(value="/accounts/events")
	public List<AccountEvent> getAccountEvent() {
		return this.accountService.getAccountEvent();
	}
	
//	@PostMapping(value="/accounts/{id}")
//	public boolean editAccount(@RequestBody Account account, @PathVariable long id) throws Exception {
//		return this.accountService.editAccountAndCreatePublishEvent(account, id);
//	}
	
}
