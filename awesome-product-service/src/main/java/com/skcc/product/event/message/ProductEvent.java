package com.skcc.product.event.message;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class ProductEvent {
	
	private long id;
	private String domain;
	private long productId;
	private ProductEventType eventType;
	private ProductPayload payload;
	private String txId;
	private String createdAt;
	
	public ProductEvent(long id, String domain, long productId, ProductEventType eventType, ProductPayload payload,
			String txId, String createdAt) {
		super();
		this.id = id;
		this.domain = domain;
		this.productId = productId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}
	
	public ProductEvent() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public ProductEventType getEventType() {
		return eventType;
	}

	public void setEventType(ProductEventType eventType) {
		this.eventType = eventType;
	}

	public ProductPayload getPayload() {
		return payload;
	}

	public void setPayload(ProductPayload payload) {
		this.payload = payload;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "ProductEvent [id=" + id + ", domain=" + domain + ", productId=" + productId + ", eventType=" + eventType
				+ ", payload=" + payload + ", txId=" + txId + ", createdAt=" + createdAt + "]";
	}
	
}
