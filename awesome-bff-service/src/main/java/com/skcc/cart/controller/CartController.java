package com.skcc.cart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.skcc.cart.domain.Cart;
import com.skcc.cart.service.CartService;

@RestController
@RequestMapping("/v1")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

	private CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PutMapping(value="/carts")
	public boolean addCart(WebSession session, @RequestBody Cart cart) throws Exception {
        // HttpSession session = request.getSession();
        log.debug("/carts called");

		if(session.getAttribute("username") == null) {
			throw new Exception();
		}
		
		return cartService.addCart((long) session.getAttribute("id"), cart);
	}
	
	@DeleteMapping(value="/carts/{id}")
	public void deleteCart(@PathVariable long id) {
        log.debug("/carts/{id} called");

		this.cartService.deleteCart(id);
	}
	
}
