
public class Cart {
	private static Cart instance;
	
	private CartContents cartItems;
	private String custName;
	private String custAddr;
	private String custCardType;
	private int custCardNum;
	
	private Cart(CartBuilder builder) {
		this.cartItems = builder.cartItems;
		this.custName = builder.custName;
		this.custAddr = builder.custAddr;
		this.custCardType = builder.custCardType;
		this.custCardNum = builder.custCardNum;
	}
	
	private static Cart createCart(CartBuilder builder) {
		if (instance == null) {
			instance = new Cart(builder);
		}
		return instance;
	}
	
	public void clearCartContents() {
		this.cartItems = new CartContents();
	}
	
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
	
	public static class CartBuilder {
		private String custName;
		private String custAddr;
		private String custCardType;
		private int custCardNum;
		
		private CartContents cartItems;
		
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
		
		public CartBuilder setCartItems(CartContents cartItems) {
			this.cartItems = cartItems;
			return this;
		}
		
		public Cart build() {
			return createCart(this);
		}
	}
	
}
