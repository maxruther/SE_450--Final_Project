
public class ProductFactory {
	
	public Product createProduct(String type, String name, double price) {
		Product result = null;
		if (type.equalsIgnoreCase("grocery")) return new GroceryItem(type, name, price);
		else if (type.equalsIgnoreCase("furniture")) return new FurnitureItem(type, name, price);
		else System.out.println("Items of this type not sold.");
		return result;
	}
}
