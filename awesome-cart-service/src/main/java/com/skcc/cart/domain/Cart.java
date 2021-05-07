package com.skcc.cart.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Cart {
	
	private long id;
	private long accountId;
	private long productId;
	private String productActive;
	private long productQuantity;
	private CartProduct productInfo;
	private String createdAt;
	
	public Cart(long id, long accountId, long productId, String productActive, long productQuantity,
			CartProduct productInfo, String createdAt) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.productId = productId;
		this.productActive = productActive;
		this.productQuantity = productQuantity;
		this.productInfo = productInfo;
		this.createdAt = createdAt;
	}
	
	public Cart() {}

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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", accountId=" + accountId + ", productId=" + productId + ", productActive="
				+ productActive + ", productQuantity=" + productQuantity + ", productInfo=" + productInfo
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
