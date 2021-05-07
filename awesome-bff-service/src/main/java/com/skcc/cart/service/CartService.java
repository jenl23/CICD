package com.skcc.cart.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.cart.domain.Cart;

@Service
public class CartService {
	
	private static final Logger log = LoggerFactory.getLogger(CartService.class);

	@Value("${api.cart.url}")
	private String apiGatewayUrl;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public CartService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public boolean addCart(long accountId, Cart cart) throws Exception {
		cart.setAccountId(accountId);

		HttpEntity<Cart> requestEntity = new HttpEntity<Cart>(cart);
		ResponseEntity<Boolean> responseEntity = this.restTemplate.exchange(String.format("%s%s", apiGatewayUrl, "/v1/carts"), HttpMethod.PUT, requestEntity, boolean.class);
		
		if(!responseEntity.getBody()) {
			throw new Exception();
		}
		
		return responseEntity.getBody();
	}
	
	// WebFlux target - aggregation
	public List<Cart> getCartsByAccountId(long accountId){        
		return Arrays.asList(this.restTemplate.getForObject(String.format("%s%s%d", apiGatewayUrl, "/v1/carts/", accountId), Cart[].class));
    }

    public Supplier<List<Cart>> getCartsByAccountIdSupplier(final long accountId) {
        return () -> this.getCartsByAccountId(accountId);
    }

	public void deleteCart(long id) {
		this.restTemplate.delete(String.format("%s%s%d", apiGatewayUrl, "/v1/carts/", id));
	}
	
}
