package com.skcc.payment.event.message;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class PaymentEvent {
	
	private long id;
	private String domain;
	private long paymentId;
	private PaymentEventType eventType;
	private PaymentPayload payload;
	private String txId;
	private String createdAt;
	
	public PaymentEvent(long id, String domain, long paymentId, PaymentEventType eventType, PaymentPayload payload,
			String txId, String createdAt) {
		super();
		this.id = id;
		this.domain = domain;
		this.paymentId = paymentId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}

	public PaymentEvent() {}

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

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public PaymentEventType getEventType() {
		return eventType;
	}

	public void setEventType(PaymentEventType eventType) {
		this.eventType = eventType;
	}

	public PaymentPayload getPayload() {
		return payload;
	}

	public void setPayload(PaymentPayload payload) {
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
		return "PaymentEvent [id=" + id + ", domain=" + domain + ", paymentId=" + paymentId + ", eventType=" + eventType
				+ ", payload=" + payload + ", txId=" + txId + ", createdAt=" + createdAt + "]";
	}
	
	
}
