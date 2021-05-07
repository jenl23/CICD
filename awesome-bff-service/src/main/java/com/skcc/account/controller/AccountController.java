package com.skcc.account.controller;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.skcc.account.domain.Account;
import com.skcc.account.service.AccountService;

@RestController
@RequestMapping("/v1")
public class AccountController {

	private AccountService accountService;
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/login")
	public Account login(WebSession session
					, @RequestBody Account account) throws Exception {
		
		// HttpSession session = request.getSession();

		if (session.getAttribute("username") != null) {
			session.getAttributes().remove("id");
			session.getAttributes().remove("username");
			session.getAttributes().remove("name");
			session.getAttributes().remove("mobile");
			session.getAttributes().remove("address");
			session.getAttributes().remove("scope");
		}

		Account resultAccount = this.accountService.login(account);
		
		if(resultAccount.getUsername() != null) {
			session.getAttributes().put("id", resultAccount.getId());
			session.getAttributes().put("username", resultAccount.getUsername());
			session.getAttributes().put("name", resultAccount.getName());
			session.getAttributes().put("mobile", resultAccount.getMobile());
			session.getAttributes().put("address", resultAccount.getAddress());
			session.getAttributes().put("scope", resultAccount.getScope());
		} else {
			throw new Exception();
		}
		
		return resultAccount;
	}
	
	@PostMapping("/accounts")
	public boolean createAccount(WebSession session, @RequestBody Account account) throws Exception {
		// HttpSession session = request.getSession();

		if (session.getAttribute("username") != null) {
			session.getAttributes().remove("id");
			session.getAttributes().remove("username");
			session.getAttributes().remove("name");
			session.getAttributes().remove("mobile");
			session.getAttributes().remove("address");
			session.getAttributes().remove("scope");
		}
		
		if(!this.accountService.createAccount(account)) {
			throw new Exception();
		}
		
		return true;
	}
	
}
