package com.skcc.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.account.domain.Account;

@Service
public class AccountService {
	
	private RestTemplate restTemplate;
	
	@Value("${api.account.url}")
	private String apiGatewayUrl;

	@Autowired
	public AccountService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Account login (Account account) {
		return this.restTemplate.postForObject(String.format("%s%s", apiGatewayUrl, "/v1/login"), account, Account.class);
	}
	
	public Account findById(long id) {
		return this.restTemplate.getForObject(String.format("%s%s%d", apiGatewayUrl, "/v1/accounts/" ,id), Account.class);
	}
	
	public boolean createAccount(Account account) {
		return this.restTemplate.postForObject(String.format("%s%s", apiGatewayUrl, "/v1/accounts"), account, boolean.class);

//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);
//		
//		ResponseEntity<?> responseEntity = this.restTemplate.exchange(String.format("%s%s", apiGatewayUrl, "v1/accounts"), HttpMethod.PUT, entity, boolean.class);
//		
//		return (boolean) responseEntity.getBody();
	}
	
}
