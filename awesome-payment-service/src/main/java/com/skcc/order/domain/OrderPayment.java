package com.skcc.order.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class OrderPayment {
	
	private long id;
	private long accountId;
	private long orderId;
	private String paymentMethod;
	private String paymentDetail1;
	private String paymentDetail2;
	private String paymentDetail3;
	private long price;
	private String paid;
	private String active;
	
	public OrderPayment(long id, long accountId, long orderId, String paymentMethod, String paymentDetail1,
			String paymentDetail2, String paymentDetail3, long price, String paid, String active) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.paymentDetail1 = paymentDetail1;
		this.paymentDetail2 = paymentDetail2;
		this.paymentDetail3 = paymentDetail3;
		this.price = price;
		this.paid = paid;
		this.active = active;
	}
	
	public OrderPayment() {}

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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentDetail1() {
		return paymentDetail1;
	}

	public void setPaymentDetail1(String paymentDetail1) {
		this.paymentDetail1 = paymentDetail1;
	}

	public String getPaymentDetail2() {
		return paymentDetail2;
	}

	public void setPaymentDetail2(String paymentDetail2) {
		this.paymentDetail2 = paymentDetail2;
	}

	public String getPaymentDetail3() {
		return paymentDetail3;
	}

	public void setPaymentDetail3(String paymentDetail3) {
		this.paymentDetail3 = paymentDetail3;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "OrderPayment [id=" + id + ", accountId=" + accountId + ", orderId=" + orderId + ", paymentMethod="
				+ paymentMethod + ", paymentDetail1=" + paymentDetail1 + ", paymentDetail2=" + paymentDetail2
				+ ", paymentDetail3=" + paymentDetail3 + ", price=" + price + ", paid=" + paid + ", active=" + active
				+ "]";
	}
	
	

}
