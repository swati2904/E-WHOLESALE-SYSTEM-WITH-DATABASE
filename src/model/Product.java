package model;

public class Product {
	private String productId;
	private String productName;
	private String description;
	private int productPrice;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	
	 public Product(String productId, String productName, String description, int productPrice) {
			this.productId = productId;
			this.productName = productName;
			this.description = description;
			this.productPrice = productPrice;
	   }
}
