package com.skcc.accountbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.accountbank.domain.AccountBank;

@Service
public class AccountBankService {
	
	@Value("${api.account.url}")
	private String apiGatewayUrl;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public AccountBankService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	// WebFlux target - aggregation
	public AccountBank findAccountBankByAccountId(long accountId) {
		return this.restTemplate.getForObject(String.format("%s%s%d", apiGatewayUrl, "/v1/accountbanks/", accountId), AccountBank.class);
	}
}
