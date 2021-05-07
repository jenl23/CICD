package com.skcc.account.event.message;

public class AccountEvent {
	
	private long id;
	private String domain;
	private long accountId;
	private AccountEventType eventType;
	private AccountPayload payload;
	private String txId;
	private String createdAt;
	
	public AccountEvent(long id, String domain, long accountId, AccountEventType eventType, AccountPayload payload,
			String txId, String createdAt) {
		super();
		this.id = id;
		this.domain = domain;
		this.accountId = accountId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}
	
	public AccountEvent() {}

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

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public AccountEventType getEventType() {
		return eventType;
	}

	public void setEventType(AccountEventType eventType) {
		this.eventType = eventType;
	}

	public AccountPayload getPayload() {
		return payload;
	}

	public void setPayload(AccountPayload payload) {
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
		return "AccountEvent [id=" + id + ", domain=" + domain + ", accountId=" + accountId + ", eventType=" + eventType
				+ ", payload=" + payload + ", txId=" + txId + ", createdAt=" + createdAt + "]";
	}
	
}
