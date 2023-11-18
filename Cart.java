import java.util.ArrayList;

public class Cart {
	private static Cart instance;
	
	private ArrayList<Product> cartItems = new ArrayList<>();
	private ProductFactory prodFact;
	private int cartSize;
	
	private String custName;
	
	private String custAddr;
	private String custCardType;
	
	private Cart(String custName) {
		this.custName = custName;
		this.cartSize = 0;
		prodFact = new ProductFactory();
	};
	
	public static Cart createCart(String custName) {
		if (instance == null) {
			instance = new Cart(custName);
		}
		return instance;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustAddr() {
		return custAddr;
	}

	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}

	public String getCustCardType() {
		return custCardType;
	}

	public void setCustCardType(String custCardType) {
		this.custCardType = custCardType;
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
