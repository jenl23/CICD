package com.skcc.accountbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.service.AccountBankService;

@RestController
@RequestMapping("/v1")
public class AccountBankController {
	
	private AccountBankService accountBankService;
	
	@Autowired
	public AccountBankController(AccountBankService accountBankService) {
		this.accountBankService = accountBankService;
	}

	@GetMapping(value="/accountbanks/{accountId}")
	public AccountBank findAccountBankByAccountId(@PathVariable long accountId) {
		return accountBankService.findAccountBankByAccountId(accountId);
	}
	
}
