package com.skcc.order.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.skcc.order.domain.Order;
import com.skcc.order.event.message.OrderEvent;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {

	List<Order> findOrderByAccountId(long accountId);
	
	boolean createOrder(Order order);
	
	boolean cancelOrder(long id);
	
	boolean payOrder(Order order);
	
	long getOrderEventId();

	long getOrderId();
	
	void createOrderEvent(OrderEvent orderEvent);
	
	Order findOrderById(long id);
	
	void setOrderPaymentId(Order order);
	
	OrderEvent findOrderEventByTxId(@Param("txId") String txId, @Param("eventType") String eventType);
	
	List<OrderEvent> getOrderEvent();
} 
