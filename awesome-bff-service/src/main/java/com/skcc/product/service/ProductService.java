package com.skcc.product.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.product.domain.Product;

@Service
public class ProductService {

	@Value("${api.product.url}")
	private String apiGatewayUrl;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public ProductService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	// WebFlux target - single api
	public List<Product> getAllProducts(){
		return Arrays.asList(this.restTemplate.getForObject(String.format("%s%s", apiGatewayUrl, "/v1/products"), Product[].class));
	}
	// public Flux<Product> getAllProducts(){
	// 	// return webClient.get()
	// 	// 	.uri("/v1/products")
	// 	// 	.retrieve()
	// 	// 	.bodyToFlux(Product.class);

	// 	return Flux.fromIterable(
	// 		Arrays.asList(
	// 			this.restTemplate.getForObject(
	// 				String.format("%s%s", apiGatewayUrl, "/v1/products"),
	// 				Product[].class
	// 			)
	// 		)
	// 	);
	// }
	
}
