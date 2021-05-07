package com.skcc.cart.subscribe;

import com.skcc.modern.pattern.message.util.Message;
import com.skcc.modern.pattern.message.util.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skcc.cart.service.CartService;
import com.skcc.product.event.message.ProductEvent;

@Component
public class CartSubscribe {

	private CartService cartService;
	
	private static final Logger log = LoggerFactory.getLogger(CartSubscribe.class);
	
	@Autowired
	public CartSubscribe(CartService cartService) {
		this.cartService = cartService;
	}

	@MessageListener(topics = {"ProductSoldOut.product"})
	public void setProductActiveToInactive(Message message) {
		ProductEvent productEvent = message.getPayloadAsType(ProductEvent.class);
		this.cartService.setCartProductInactiveAndProductInfoAndCreatePublishEvent(productEvent);
	}

	@MessageListener(topics = {"ProductAmountAdded.product"})
	public void setProductAmountAdded(Message message) {
		ProductEvent productEvent = message.getPayloadAsType(ProductEvent.class);
		this.cartService.setCartProductActiveAndProductInfoAndCreatePublishEvent(productEvent);
	}

}
