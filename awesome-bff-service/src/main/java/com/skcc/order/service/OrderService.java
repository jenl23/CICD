package com.skcc.order.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.order.domain.Order;

@Service
public class OrderService {
	
	@Value("${api.order.url}")
	private String apiGatewayUrl;
	
	private RestTemplate restTemplate;
	
	public OrderService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public boolean createOrder(Order order) throws Exception {
		HttpEntity<Order> requestEntity = new HttpEntity<Order>(order);
		ResponseEntity<Boolean> responseEntity = this.restTemplate.exchange(String.format("%s%s", apiGatewayUrl, "/v1/orders"), HttpMethod.PUT, requestEntity, boolean.class);
	
		if(!responseEntity.getBody()) {
			throw new Exception();
		}
		
		return responseEntity.getBody();
	}
}
