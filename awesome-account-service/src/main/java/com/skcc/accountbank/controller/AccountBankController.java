package com.skcc.accountbank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.service.AccountBankService;

@RestController
@RequestMapping("/v1")
public class AccountBankController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountBankController.class);

	private AccountBankService accountBankService;
	
	@Autowired
	public AccountBankController(AccountBankService accountBankService) {
		this.accountBankService = accountBankService;
	}
	
	@GetMapping(value="/accountbanks/{accountId}")
	public AccountBank findAccountBankByAccountId(@RequestHeader("X-TXID") String txId, @PathVariable long accountId) {
		log.info(String.valueOf(accountId));
		return accountBankService.findAccountBankByAccountId(txId, accountId);
	}
	
}
