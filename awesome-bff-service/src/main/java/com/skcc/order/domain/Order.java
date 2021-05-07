package com.skcc.order.domain;

import java.util.List;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Order {
	
	private long id;
	private long accountId;
	private long paymentId;
	private OrderAccount accountInfo;
	private List<OrderProduct> productsInfo;
	private OrderPayment paymentInfo;
	private String paid;
	private String status;
	private String createdAt;
	
	public Order(long id, long accountId, long paymentId, OrderAccount accountInfo, List<OrderProduct> productsInfo,
			OrderPayment paymentInfo, String paid, String status, String createdAt) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.paymentId = paymentId;
		this.accountInfo = accountInfo;
		this.productsInfo = productsInfo;
		this.paymentInfo = paymentInfo;
		this.paid = paid;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	public Order() {}

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

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public OrderAccount getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(OrderAccount accountInfo) {
		this.accountInfo = accountInfo;
	}

	public List<OrderProduct> getProductsInfo() {
		return productsInfo;
	}

	public void setProductsInfo(List<OrderProduct> productsInfo) {
		this.productsInfo = productsInfo;
	}

	public OrderPayment getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(OrderPayment paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", accountId=" + accountId + ", paymentId=" + paymentId + ", accountInfo="
				+ accountInfo + ", productsInfo=" + productsInfo + ", paymentInfo=" + paymentInfo + ", paid=" + paid
				+ ", status=" + status + ", createdAt=" + createdAt + "]";
	}
	
	
}
