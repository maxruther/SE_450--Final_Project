
public class Cart {
	private static Cart instance;
	
	private cartContents cartItems;
	
	public cartContents getCartItems() {
		return cartItems;
	}

	public void setCartItems(cartContents cartItems) {
		this.cartItems = cartItems;
	}

	private String custName;
	private String custAddr;
	private String custCardType;
	
	private Cart(String custName) {
		this.custName = custName;
		this.cartItems = new cartContents();
	}
	
	public static Cart createCart(String custName) {
		if (instance == null) {
			instance = new Cart(custName);
		}
		return instance;
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
	
}
