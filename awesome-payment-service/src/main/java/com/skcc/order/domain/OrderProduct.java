package com.skcc.order.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class OrderProduct{
	
	private long id;
	private String name;
	private long categoryId;
	private String active;
	private long quantity;
	private long originalPrice;
	private long salePercentage;
	private long salePrice;
	private long resultPrice;
	private long amount;
	private String img;
	
	public OrderProduct(long id, String name, long categoryId, String active, long quantity, long originalPrice,
			long salePercentage, long salePrice, long resultPrice, long amount, String img) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.active = active;
		this.quantity = quantity;
		this.originalPrice = originalPrice;
		this.salePercentage = salePercentage;
		this.salePrice = salePrice;
		this.resultPrice = resultPrice;
		this.amount = amount;
		this.img = img;
	}
	
	public OrderProduct() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(long originalPrice) {
		this.originalPrice = originalPrice;
	}

	public long getSalePercentage() {
		return salePercentage;
	}

	public void setSalePercentage(long salePercentage) {
		this.salePercentage = salePercentage;
	}

	public long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}

	public long getResultPrice() {
		return resultPrice;
	}

	public void setResultPrice(long resultPrice) {
		this.resultPrice = resultPrice;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", active=" + active
				+ ", quantity=" + quantity + ", originalPrice=" + originalPrice + ", salePercentage=" + salePercentage
				+ ", salePrice=" + salePrice + ", resultPrice=" + resultPrice + ", amount=" + amount + ", img=" + img
				+ "]";
	}
	
	
	
}
