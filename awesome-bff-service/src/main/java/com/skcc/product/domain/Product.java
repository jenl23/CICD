package com.skcc.product.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Product {
	
	private long id;
	private String name;
	private String categoryName;
	private String active;
	private long originalPrice;
	private long salePercentage;
	private long salePrice;
	private long resultPrice;
	private long amount;
	private String img;
	private String createdAt;
	
	public Product(long id, String name, String categoryName, String active, long originalPrice, long salePercentage,
			long salePrice, long resultPrice, long amount, String img, String createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.categoryName = categoryName;
		this.active = active;
		this.originalPrice = originalPrice;
		this.salePercentage = salePercentage;
		this.salePrice = salePrice;
		this.resultPrice = resultPrice;
		this.amount = amount;
		this.img = img;
		this.createdAt = createdAt;
	}
	
	public Product() {}

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", categoryName=" + categoryName + ", active=" + active
				+ ", originalPrice=" + originalPrice + ", salePercentage=" + salePercentage + ", salePrice=" + salePrice
				+ ", resultPrice=" + resultPrice + ", amount=" + amount + ", img=" + img + ", createdAt=" + createdAt
				+ "]";
	}
	
}
