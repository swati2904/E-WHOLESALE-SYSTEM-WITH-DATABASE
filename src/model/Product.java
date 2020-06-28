package model;

public class Product {

	private String productId;
	private String productName;
	private int price;
	private String Description;

	public Product(String id, String name, int price, String desc) {
		this.productId = id;
		this.productName = name;
		this.price = price;
		this.Description = desc;
	}

	public Product() {

	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
