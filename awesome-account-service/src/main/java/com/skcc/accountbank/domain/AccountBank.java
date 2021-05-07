package com.skcc.accountbank.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class AccountBank {
	
	private long id;
	private long accountId;
	private String bankName;
	private String bankNumber;
	private String active;
	private String createdAt;
	
	public AccountBank(long id, long accountId, String bankName, String bankNumber, String active, String createdAt) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.bankName = bankName;
		this.bankNumber = bankNumber;
		this.active = active;
		this.createdAt = createdAt;
	}
	
	public AccountBank() {}

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "AccountBank [id=" + id + ", accountId=" + accountId + ", bankName=" + bankName + ", bankNumber="
				+ bankNumber + ", active=" + active + ", createdAt=" + createdAt + "]";
	}
	
	
	
}
