package com.skcc.product.subscribe;

import com.skcc.modern.pattern.message.util.Message;
import com.skcc.modern.pattern.message.util.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skcc.order.event.message.OrderEvent;
import com.skcc.product.service.ProductService;

@Component
public class ProductSubscribe {

	private ProductService productService;
	
	private static final Logger log = LoggerFactory.getLogger(ProductSubscribe.class);
	
	@Autowired
	public ProductSubscribe(ProductService productService) {
		this.productService = productService;
	}

	@MessageListener(topics = {"OrderCreated.order"})
	public void receiveOrderCreatedEvent(Message message) {
		OrderEvent orderEvent = message.getPayloadAsType(OrderEvent.class);
		this.productService.subtractProductAmountAndCreatePublishProductEvent(orderEvent);
	}

	@MessageListener(topics = {"OrderCanceled.order"})
	public void receiveOrderCanceledEvent(Message message) {
		OrderEvent orderEvent = message.getPayloadAsType(OrderEvent.class);
		this.productService.addProductAmountAndCreatePublishProductEvent(orderEvent);
	}
	
}
