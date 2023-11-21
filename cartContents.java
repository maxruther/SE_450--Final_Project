import java.util.ArrayList;

public class CartContents implements ProductList {
	private ArrayList<Product> cartItems;
	private ProductFactory prodFact;
	private int cartSize;
	private int cartVariety;
	private boolean isSaleReady;
	
	public CartContents() {
		cartSize = 0;
		cartVariety = 0;
		prodFact = new ProductFactory();
		cartItems = new ArrayList<>();
		isSaleReady = false;
	}
	
	public void addToCart(Product prod, int n) {
		
		if (n <= 0) return;
		
		Product newProd = prodFact.createProduct(prod.getType(),
					prod.getName(), 
					prod.getPrice(),
					prod.getProdID());
		
		boolean prodContained = false;
		for (int i = 0; i < cartVariety; i++) {
			Product p = cartItems.get(i);
			if (prod.getProdID() == p.getProdID()) {
				p.setQty( p.getQty() + n );
				cartSize = cartSize + n;
				prodContained = true;
				break;
			}
		}
		
		if (!prodContained) {
			cartSize = cartSize + n;
			cartVariety++;
			
			newProd.setQty(n);
			newProd.setProductNum(cartVariety);
			cartItems.add(newProd);
		}
		
	}
	
	public void removeFromCart(Product prod, int n) {
		if (n <= 0) return; 

		boolean prodContained = false;
		for (int i = 0; i < cartVariety; i++) {
			Product p = cartItems.get(i);
			if (prod.getProdID() == p.getProdID()) {
				p.setQty( Math.max(0, p.getQty() - n));
				cartSize = Math.max(0, cartSize - n);
				
				if (p.getQty() == 0) {
					cartItems.remove(i);
					cartVariety--;
				}
				prodContained = true;
				break;
			}
		}
		
		if (!prodContained) {
			System.out.println("Sorry, that item was not found in the cart.");
		}
	}
	
	@Override
	public void printProdList() {
		System.out.println("\nITEMS IN CART");
		System.out.println("----------------------\n");
		
		for (Product p: cartItems) {
			System.out.println(p.getProductNum() + ")");
			System.out.println(p.toString() + " || Qty in cart: " + p.getQty());
		}
	}

	@Override
	public Product getItem(int n) {
		Product requestedItem = cartItems.get(n - 1);
		return requestedItem;
	}

	@Override
	public int getVariety() {
		return this.cartVariety;
	}

	@Override
	public int getSize() {
		return this.cartSize;
	}
	
	public ArrayList<Product> getCartItems() {
		return cartItems;
	}

	public void setCartItems(ArrayList<Product> cartItems) {
		this.cartItems = cartItems;
	}

	public boolean isSaleReady() {
		return isSaleReady;
	}

	public void setSaleReady(boolean isSaleReady) {
		this.isSaleReady = isSaleReady;
	}
}
