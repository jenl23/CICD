package com.skcc.cart.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.skcc.cart.domain.Cart;
import com.skcc.cart.event.message.CartEvent;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CartMapper {
	
//	@Select("select * from carts where accountId=#{accountId}")
	public List<Cart> findCartByAccountId(long accountId);
	
//	@Select("select * from carts where id = #{id}")
	public Cart findById(long id);
	 
//	@Insert("insert into carts(accountId, productsInfo, createdAt) values(#{accountId}, #{productsInfo}, NOW())")
	public void addCart(Cart cart);
	
//	@Delete("delete from carts where id = #{id}")
	public boolean deleteCart(long id);
	
//	@Insert("insert into cart_events(id, domain, cartId, eventType, payload, txId, createdAt) values(#{id}, #{domain}, #{cartId}, #{eventType}, #{payload}, #{txId}, NOW())")
	public void createCartEvent(CartEvent cartEvent);
	
//	@Select("select get_account_eventid_seq('eventId')")
	public long getCartEventId();
	
//	@Select("select * from carts where productId=#{productId}")
	public long findCartByProductId(@Param("accountId") long accountId, @Param("productId") long productId);
	
//	@update("update carts set productActive = 'inactive', productInfo = #{productInfo} where productId = #{prouctId} and productActive = 'active'")
	public void setCartProductInactiveAndProductInfo(Cart cart);
	
//	@update("update carts set productActive = 'active', productInfo = #{productInfo} where productId = #{prouctId} and productActive = 'inactive'")
	public void setCartProductActiveAndProductInfo(Cart cart);
	
//	@Select("select * from carts where productId = #{prouctId} and productActive = 'active'")
	public List<Cart> findCartToBeProductInactiveById(long productId);

//	@Select("select * from carts where productId = #{prouctId} and productActive = 'active'")
	public List<Cart> findCartToBeProductActiveById(long productId);
	
//	update("update carts set productQuantity = #{quantity} where id = #{id}")
	public void setCartQuantity(@Param("id") long id, @Param("quantity") long quantity);
	
//	@select("select * from cart_events")
	public List<CartEvent> getCartEvent();
}
