package com.skcc.accountbank.event.message;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class AccountBankEvent {
	
	private long id;
	private String domain;
	private long accountBankId;
	private AccountBankEventType eventType;
	private AccountBankPayload payload;
	private String txId;
	private String createdAt;
	
	public AccountBankEvent(long id, String domain, long accountBankId, AccountBankEventType eventType,
			AccountBankPayload payload, String txId, String createdAt) {
		super();
		this.id = id;
		this.domain = domain;
		this.accountBankId = accountBankId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}
	
	public AccountBankEvent() {}

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

	public long getAccountBankId() {
		return accountBankId;
	}

	public void setAccountBankId(long accountBankId) {
		this.accountBankId = accountBankId;
	}

	public AccountBankEventType getEventType() {
		return eventType;
	}

	public void setEventType(AccountBankEventType eventType) {
		this.eventType = eventType;
	}

	public AccountBankPayload getPayload() {
		return payload;
	}

	public void setPayload(AccountBankPayload payload) {
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
		return "AccountBankEvent [id=" + id + ", domain=" + domain + ", accountBankId=" + accountBankId + ", eventType="
				+ eventType + ", payload=" + payload + ", txId=" + txId + ", createdAt=" + createdAt + "]";
	}
	
	
}
