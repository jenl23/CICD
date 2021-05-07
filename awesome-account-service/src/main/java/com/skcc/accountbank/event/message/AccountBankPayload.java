package com.skcc.accountbank.event.message;

//@DATA
//@NOARGSCONSTRUCTOR
//@ALLARGSCONSTRUCTOR
//@TOSTRING
public class AccountBankPayload {
	
	private long id;
	private long accountId;
	private String bankName;
	private String bankNumber;
	private String active;
	
	public AccountBankPayload(long id, long accountId, String bankName, String bankNumber, String active) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.bankName = bankName;
		this.bankNumber = bankNumber;
		this.active = active;
	}
	
	public AccountBankPayload() {}

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

	@Override
	public String toString() {
		return "AccountBankPayload [id=" + id + ", accountId=" + accountId + ", bankName=" + bankName + ", bankNumber="
				+ bankNumber + ", active=" + active + "]";
	}
	
	
}
