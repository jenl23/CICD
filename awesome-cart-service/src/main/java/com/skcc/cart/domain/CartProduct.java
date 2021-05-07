package com.skcc.cart.domain;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class CartProduct{
	
	private long id;
	private String name;
	private String categoryName;
	private long amount;
	private long originalPrice;
	private long salePercentage;
	private long salePrice;
	private long resultPrice;
	private String img;
	
	public CartProduct(long id, String name, String categoryName, long amount, long originalPrice, long salePercentage,
			long salePrice, long resultPrice, String img) {
		super();
		this.id = id;
		this.name = name;
		this.categoryName = categoryName;
		this.amount = amount;
		this.originalPrice = originalPrice;
		this.salePercentage = salePercentage;
		this.salePrice = salePrice;
		this.resultPrice = resultPrice;
		this.img = img;
	}
	
	public CartProduct() {}

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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "CartProduct [id=" + id + ", name=" + name + ", categoryName=" + categoryName + ", amount=" + amount
				+ ", originalPrice=" + originalPrice + ", salePercentage=" + salePercentage + ", salePrice=" + salePrice
				+ ", resultPrice=" + resultPrice + ", img=" + img + "]";
	}
	
}
