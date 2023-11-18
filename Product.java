
public abstract class Product {
	private String name;
	private double price;
	private String type;
	
	private int productNum;
	
	public Product(String type, String name, double price) {
		this.type = type;
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public String toString() {
		return this.name + "\n" +
				"Price: $" + this.price + "\n" +
				"Category: " + this.type + "\n";
	}
	
}
