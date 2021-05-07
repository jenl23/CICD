package com.skcc.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.skcc.product.domain.Product;
import com.skcc.product.event.message.ProductEvent;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductMapper {
	
	List<Product> findByCategoryId(long categoryId);
	
	List<Product> findProductOnSale();
	
	List<Product> getAllProducts();
	
	Product findById(long id);
	
	long setProductInactive(long id);
	 
	long addProductAmount(@Param("id") long id, @Param("quantity") long quantity);
	
	long subtractProductAmount(@Param("id") long id, @Param("quantity") long quantity);
	
	void createProductEvent(ProductEvent productEvent);
	
	long getProductEventId();
	
	ProductEvent findProductEvent(@Param("productId") long productId, @Param("txId") String txId,@Param("eventType") String eventType);
	
	List<ProductEvent> getProductEvent();
	
}
