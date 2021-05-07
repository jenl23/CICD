package com.skcc.account.event.message;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class AccountPayload {
	
	private long id;
	private String username;
	private String name;
	private String mobile;
	private String scope;
	private String address;
	
	public AccountPayload(long id, String username, String name, String mobile, String scope, String address) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.mobile = mobile;
		this.scope = scope;
		this.address = address;
	}
	
	public AccountPayload() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "AccountPayload [id=" + id + ", username=" + username + ", name=" + name + ", mobile=" + mobile
				+ ", scope=" + scope + ", address=" + address + "]";
	}
	
}
