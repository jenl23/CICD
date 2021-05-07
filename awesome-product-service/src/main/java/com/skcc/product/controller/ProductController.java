package com.skcc.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.product.domain.Product;
import com.skcc.product.event.message.ProductEvent;
import com.skcc.product.service.ProductService;

@RestController
@RequestMapping("/v1")
public class ProductController {
    
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	private ProductService productService;
	
	@Autowired
	public ProductController (ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value="/products/category/{categoryId}")
	public List<Product> findByCategoryId(@PathVariable long categoryId){
        logger.info("call findByCategoryId");
		return this.productService.findByCategoryId(categoryId);
	}

	@Cacheable(value = "redisCache", key = "#root.method.name")
	@GetMapping(value="/products")
	public List<Product> getAllProducts(){
        logger.info("call getAllProducts");
		return this.productService.getAllProducts();
	}
	
	@GetMapping(value="/products/sale")
	public List<Product> findByCategoryId(){
        logger.info("call findByCategoryId");
		return this.productService.findProductOnSale();
	}
	
	@GetMapping(value="/products/{id}")
	public Product findById(@PathVariable(value="id") long id) {
        logger.info("call findById");
		return this.productService.findById(id);
	}
	
	@GetMapping(value="/products/events")
	public List<ProductEvent> getProductEvent(){
        logger.info("call getProductEvent");
		return this.productService.getProductEvent();
	}
}
