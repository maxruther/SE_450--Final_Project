
public class ProductFactory {
	
	public Product createProduct(String type, String name, double price, int prodID) {
		Product result = null;
		if (type.equalsIgnoreCase("grocery")) return new GroceryItem(type, name, price, prodID);
		else if (type.equalsIgnoreCase("furniture")) return new FurnitureItem(type, name, price, prodID);
		else if (type.equalsIgnoreCase("clothing")) return new ClothingItem(type, name, price, prodID);
		else System.out.println("Items of this type not sold.");
		return result;
	}
}
