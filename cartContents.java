import java.util.ArrayList;

public class cartContents {
	private ArrayList<Product> cartItems;
	private ProductFactory prodFact;
	private int cartSize;
	
	public cartContents() {
		cartSize = 0;
		prodFact = new ProductFactory();
		cartItems = new ArrayList<>();
	}
	
	public void addToCart(Product prod) {
		
		Product newProd = prodFact.createProduct(prod.getType(),
				  								prod.getName(), 
				  								prod.getPrice());
		cartSize++;
		newProd.setProductNum(cartSize);
		
		cartItems.add(newProd);
	}
	
	public void addToCart(Product prod, int n) {
		
		for (int i = 0; i < n; i++) {
			addToCart(prod);
		}
		
	}
	
	public void removeFromCart(int i) {
		for (int k = i; k < cartSize; k++) {
			cartItems.get(k).setProductNum(k);
		}
		
		cartItems.remove(i-1);
		cartSize--;
	}
	
	public void printCart() {
		System.out.println("\nITEMS IN CART");
		System.out.println("----------------------\n");
		
		for (Product p: cartItems) {
			System.out.println(p.getProductNum() + ")");
			System.out.println(p.toString() + "\n");
		}
	}
	
}
