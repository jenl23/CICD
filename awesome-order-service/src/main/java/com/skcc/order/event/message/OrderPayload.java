package com.skcc.order.event.message;

import java.util.List;

import com.skcc.order.domain.OrderAccount;
import com.skcc.order.domain.OrderPayment;
import com.skcc.order.domain.OrderProduct;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class OrderPayload {

	private long id;
	private long accountId;
	private long paymentId;
	private OrderAccount accountInfo;
	private OrderPayment paymentInfo;
	private List<OrderProduct> productsInfo;
	private String paid;
	private String status;
	
	public OrderPayload(long id, long accountId, long paymentId, OrderAccount accountInfo, OrderPayment paymentInfo,
			List<OrderProduct> productsInfo, String paid, String status) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.paymentId = paymentId;
		this.accountInfo = accountInfo;
		this.paymentInfo = paymentInfo;
		this.productsInfo = productsInfo;
		this.paid = paid;
		this.status = status;
	}
	
	public OrderPayload() {}

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

	public OrderPayment getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(OrderPayment paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public List<OrderProduct> getProductsInfo() {
		return productsInfo;
	}

	public void setProductsInfo(List<OrderProduct> productsInfo) {
		this.productsInfo = productsInfo;
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

	@Override
	public String toString() {
		return "OrderPayload [id=" + id + ", accountId=" + accountId + ", paymentId=" + paymentId + ", accountInfo="
				+ accountInfo + ", paymentInfo=" + paymentInfo + ", productsInfo=" + productsInfo + ", paid=" + paid
				+ ", status=" + status + "]";
	}
	
	
	
}
