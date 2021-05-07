package com.skcc.cart.event.message;

import com.skcc.cart.domain.CartProduct;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class CartPayload {
	
	private long id;
	private long accountId;
	private long productId;
	private String productActive;
	private long productQuantity; 
	private CartProduct productInfo;
	
	public CartPayload(long id, long accountId, long productId, String productActive, long productQuantity,
			CartProduct productInfo) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.productId = productId;
		this.productActive = productActive;
		this.productQuantity = productQuantity;
		this.productInfo = productInfo;
	}
	
	public CartPayload() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductActive() {
		return productActive;
	}

	public void setProductActive(String productActive) {
		this.productActive = productActive;
	}

	public long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public CartProduct getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(CartProduct productInfo) {
		this.productInfo = productInfo;
	}

	@Override
	public String toString() {
		return "CartPayload [id=" + id + ", accountId=" + accountId + ", productId=" + productId + ", productActive="
				+ productActive + ", productQuantity=" + productQuantity + ", productInfo=" + productInfo + "]";
	}
	
}
