import java.util.ArrayList;

public class UserCart implements ProductList{
	private String custName;
	
	private ArrayList<Product> cartItems;
	private int cartSize;
	private int cartVariety;
	private ProductFactory prodFact;
	
	private String custAddr;
	private String custCardType;
	private int custCardNum;
	
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

	public int getCustCardNum() {
		return custCardNum;
	}

	public void setCustCardNum(int custCardNum) {
		this.custCardNum = custCardNum;
	}

	private UserCart(CartBuilder builder) {
		this.cartSize = builder.cartSize;
		this.cartVariety = builder.cartVariety;
		this.prodFact = new ProductFactory();
		this.cartItems = builder.cartItems;
		
		this.custName = builder.custName;
		this.custAddr = builder.custAddr;
		this.custCardType = builder.custCardType;
		this.custCardNum = builder.custCardNum;
	}
	
	public void addToCart(Product prod) {
		
		Product newProd = prodFact.createProduct(prod.getType(),
				  								prod.getName(), 
				  								prod.getPrice(),
				  								prod.getProdID());
		
		boolean prodContained = false;
		for (int i = 0; i < cartVariety; i++) {
			Product p = cartItems.get(i);
			if (prod.getProdID() == p.getProdID()) {
				p.setQty( p.getQty() + 1 );
				cartSize++;
				prodContained = true;
				break;
			}
		}
		
		if (!prodContained) {
			cartSize++;
			cartVariety++;
			newProd.setProductNum(cartSize);
			
			cartItems.add(newProd);
		}
		
	}
	
	public void addToCart(Product prod, int n) {
		if (n == 0) return;
		
		Product newProd = prodFact.createProduct(prod.getType(),
					prod.getName(), 
					prod.getPrice(),
					prod.getProdID());
		
		boolean prodContained = false;
		for (int i = 0; i < n; i++) {
			Product p = cartItems.get(i);
			if (prod.getProdID() == p.getProdID()) {
				p.setQty( p.getQty() + n );
				cartSize = cartSize + n;
				prodContained = true;
				break;
			}
		}
		
		if (!prodContained) {
			newProd.setQty(n);
			cartSize = cartSize + n;
			newProd.setProductNum(cartSize);
			cartItems.add(newProd);
			
			cartVariety++;
		}
	}
	
	public void removeFromCart(Product prod, int n) {
		if (n == 0) return; 

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
	
	public void printCart() {
		System.out.println("\nITEMS IN CART");
		System.out.println("----------------------\n");
		
		for (Product p: cartItems) {
			System.out.println(p.getProductNum() + ")");
			System.out.println(p.toString() + " || Qty in cart: " + p.getQty());
		}
	}

	public int getSize() {
		return this.cartSize;
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
	
	public static class CartBuilder {
		private String custName;
		
		private ArrayList<Product> cartItems;
		private int cartSize;
		private int cartVariety;
		private ProductFactory prodFact;
		
		private String custAddr;
		private String custCardType;
		private int custCardNum;
		
		public CartBuilder() {
			cartSize = 0;
			cartVariety = 0;
			prodFact = new ProductFactory();
			cartItems = new ArrayList<>();
		}
		
		public CartBuilder setCustName(String name) {
			this.custName = name;
			return this;
		}
		
		public CartBuilder addToCart(Product prod, int n) {
			if (n == 0) return this;
			
			Product newProd = prodFact.createProduct(prod.getType(),
						prod.getName(), 
						prod.getPrice(),
						prod.getProdID());
			
			boolean prodContained = false;
			for (int i = 0; i < n; i++) {
				Product p = cartItems.get(i);
				if (prod.getProdID() == p.getProdID()) {
					p.setQty( p.getQty() + n );
					cartSize = cartSize + n;
					prodContained = true;
					break;
				}
			}
			
			if (!prodContained) {
				newProd.setQty(n);
				cartSize = cartSize + n;
				newProd.setProductNum(cartSize);
				cartItems.add(newProd);
				
				cartVariety++;
			}
			
			return this;
		}
		
		public CartBuilder removeFromCart(Product prod, int n) {
			if (n == 0) return this; 

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
			
			return this;
		}
		
		public CartBuilder setCustAddr(String addr) {
			this.custAddr = addr;
			return this;
		}
		
		public CartBuilder setCustCardType(String cardType) {
			this.custCardType = cardType;
			return this;
		}
		
		public CartBuilder setCustCardNum(int cardNum) {
			this.custCardNum = cardNum;
			return this;
		}
		
		public UserCart build() {
			return new UserCart(this);
		}
		
		public void printCart() {
			System.out.println("\nITEMS IN CART");
			System.out.println("----------------------\n");
			
			for (Product p: cartItems) {
				System.out.println(p.getProductNum() + ")");
				System.out.println(p.toString() + " || Qty in cart: " + p.getQty());
			}
		}
	}
	
	
}
