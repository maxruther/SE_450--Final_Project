
public abstract class Product {
	private String name;
	private double price;
	private String type;
	private int qty;

	private int prodID;
	
	private int productNum;
	
	public Product(String type, String name, double price, int prodID) {
		this.type = type;
		this.name = name;
		this.price = price;
		this.prodID = prodID;
		
		this.qty = 1;
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
	
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getProdID() {
		return prodID;
	}

	public void setProdID(int prodID) {
		this.prodID = prodID;
	}
	
	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public String toString() {
		int namePadding = 20 - this.name.length();
		int pricePadding = 12 - String.valueOf(this.price).length();
		return this.name + " ".repeat(namePadding) + " || " +
		" $" + this.price + " ".repeat(pricePadding) + " || " +
		this.type;

	}
	
}
