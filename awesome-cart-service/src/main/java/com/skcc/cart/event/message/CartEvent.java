package com.skcc.cart.event.message;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class CartEvent {
	private long id;
	private String domain;
	private long cartId;
	private CartEventType eventType;
	private CartPayload payload;
	private String txId;
	private String createdAt;
	
	public CartEvent(long id, String domain, long cartId, CartEventType eventType, CartPayload payload, String txId,
			String createdAt) {
		super();
		this.id = id;
		this.domain = domain;
		this.cartId = cartId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}
	
	public CartEvent() {}

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

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public CartEventType getEventType() {
		return eventType;
	}

	public void setEventType(CartEventType eventType) {
		this.eventType = eventType;
	}

	public CartPayload getPayload() {
		return payload;
	}

	public void setPayload(CartPayload payload) {
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
		return "CartEvent [id=" + id + ", domain=" + domain + ", cartId=" + cartId + ", eventType=" + eventType
				+ ", payload=" + payload + ", txId=" + txId + ", createdAt=" + createdAt + "]";
	}
	
}
