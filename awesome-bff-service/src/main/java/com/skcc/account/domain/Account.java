package com.skcc.account.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Account {
	
	private long id;
	private String username;
	private String name;
	private String password;
	private String mobile;
	private String scope;
	private String address;
	private String createdAt;
	
	public Account(long id, String username, String name, String password, String mobile, String scope, String address,
			String createdAt) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.mobile = mobile;
		this.scope = scope;
		this.address = address;
		this.createdAt = createdAt;
	}
	
	public Account() {}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", name=" + name + ", password=" + password
				+ ", mobile=" + mobile + ", scope=" + scope + ", address=" + address + ", createdAt=" + createdAt + "]";
	}
	
}
