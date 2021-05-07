package com.skcc.cart.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.cart.domain.Cart;
import com.skcc.cart.event.message.CartEvent;
import com.skcc.cart.service.CartService;

@RestController
@RequestMapping("/v1")
public class CartController {
	
	private CartService cartService;
	
	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping(value="/carts/{accountId}")
	public List<Cart> findCartByAccountId(@PathVariable long accountId) {
		return this.cartService.findCartByAccountId(accountId);
	}
	
	@PutMapping(value="/carts")
	public boolean addCart(@RequestBody Cart cart) {
		return cartService.addCartAndCreatePublishEvent(cart);
	}
	
	@DeleteMapping(value="/carts/{id}")
	public boolean deleteCart(@PathVariable long id) {
		return this.cartService.deleteCartAndCreatePublishEvent(id);
	}
	
	@PostMapping(value="/carts/{id}")
	public boolean editCartQuantity(@PathVariable long id, @PathParam(value = "quantity") long quantity) {
		return this.cartService.setCartQuantityAndCreatePublishEvent(id, quantity);
	}
	
	@GetMapping(value="carts/events")
	public List<CartEvent> getCartEvent() {
		return this.cartService.getCartEvent();
	}
	
}
