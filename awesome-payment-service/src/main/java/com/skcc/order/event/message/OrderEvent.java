package com.skcc.order.event.message;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class OrderEvent {
	
	private long id;
	private String domain;
	private long orderId;
	private OrderEventType eventType;
	private OrderPayload payload;
	private String txId;
	private String createdAt;
	
	public OrderEvent(long id, String domain, long orderId, OrderEventType eventType, OrderPayload payload, String txId,
			String createdAt) {
		super();
		this.id = id;
		this.domain = domain;
		this.orderId = orderId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}
	
	public OrderEvent() {}

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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public OrderEventType getEventType() {
		return eventType;
	}

	public void setEventType(OrderEventType eventType) {
		this.eventType = eventType;
	}

	public OrderPayload getPayload() {
		return payload;
	}

	public void setPayload(OrderPayload payload) {
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
		return "OrderEvent [id=" + id + ", domain=" + domain + ", orderId=" + orderId + ", eventType=" + eventType
				+ ", payload=" + payload + ", txId=" + txId + ", createdAt=" + createdAt + "]";
	}
	
	
	
}
