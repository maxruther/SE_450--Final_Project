
public class Cart {
	// The Cart essentially represent the final order, as reached by logging
	// a user in (LogIn) and taking them through the shopping module (OrderProcessor).
	//
	// Cart uses both the Singleton and the Builder patterns. The Singleton pattern
	// can be observed in its distinct constructors, while the Builder is implemented
	// as an inner static class of Cart (called CartBuilder).
	
	private static Cart instance;
	
	// The cart contents and customer info fields
	private CartContents cartItems;
	private String custName;
	private String custAddr;
	private String custCardType;
	private int custCardNum;
	
	// The Cart's private constructor is only accessed by the Singleton creator method,
	// createCart().
	private Cart(CartBuilder builder) {
		this.cartItems = builder.cartItems;
		this.custName = builder.custName;
		this.custAddr = builder.custAddr;
		this.custCardType = builder.custCardType;
		this.custCardNum = builder.custCardNum;
	}
	
	// This Singleton creator method is only accessed by the CartBuilder.build() method.
	private static Cart createCart(CartBuilder builder) {
		if (instance == null) {
			instance = new Cart(builder);
		}
		return instance;
	}
	
	// Cart's toString() is overridden, for convenient printing during PaymentProcessor's
	// order review process.
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n----------------------\n"
				+ "       ORDER REVIEW\n"
				+   "----------------------\n\n"
				+ "Ordered items: \n\n");
		double subtotal = 0;
		for (int i = 0; i < cartItems.getVariety(); i++) {
			Product p = cartItems.getItem(i+1);
			sb.append(p + " || Qty: " + p.getQty() + "\n");
			subtotal = subtotal + (p.getPrice() * p.getQty());
		}
		
		String result = 
				"\nSUBTOTAL: " + subtotal + "\n" +
				"NAME: " + custName + "\n" +
				"ADDRESS: " + custAddr + "\n\n" +
				"CARD TYPE: " + custCardType + "\n" +
				"CARD NUMBER: " + custCardNum + "\n";
		sb.append(result);
		
		return sb.toString();
	}
	
	// This cart-clearing method is used when a purchase has been completed (which is done
	// by the PaymentProcessor.)
	public void clearCartContents() {
		this.cartItems = new CartContents();
	}
	
	// Here are various getters and setters. Skip past these to see the inner Builder class 
	// at the bottom of the page.
	
	public int getCustCardNum() {
		return custCardNum;
	}

	public void setCustCardNum(int custCardNum) {
		this.custCardNum = custCardNum;
	}

	public CartContents getCartItems() {
		return cartItems;
	}

	public void setCartItems(CartContents cartItems) {
		this.cartItems = cartItems;
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
	
	// The CartBuilder class is solely responsible for the creation of a
	// Cart object.
	//
	// At the end of MainClass, the CartBuilder takes the customer info and
	// cart contents that were thus far collected by the LogIn and 
	// OrderProcessor, respectively, and creates a Cart. The Cart is then
	// used as an aggregation of that data, and is processed by the 
	// PaymentProcessor.
	public static class CartBuilder {
		private CartContents cartItems;
		private String custName;
		private String custAddr;
		private String custCardType;
		private int custCardNum;
		
		public CartBuilder setCartItems(CartContents cartItems) {
			this.cartItems = cartItems;
			return this;
		}
		
		public CartBuilder setCustName(String custName) {
			this.custName = custName;
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
		
		public Cart build() {
			return createCart(this);
		}
	}
	
}
