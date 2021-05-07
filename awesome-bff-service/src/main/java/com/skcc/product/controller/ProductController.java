package com.skcc.product.controller;

import java.util.List;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.WebSession;

import com.skcc.product.domain.Product;
import com.skcc.product.service.ProductService;

@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/product")
	public String index(WebSession session, Model model) {
        // HttpSession session = request.getSession();
        log.debug("/product called");

		if(session.getAttribute("username") == null) {
			return "sign";
		}
		
		List<Product> productsList = this.productService.getAllProducts();
		model.addAttribute(productsList);
		
		return "product";
	}
}
