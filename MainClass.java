
public class MainClass {

	public static void main(String[] args) {
		String srcPath = "src\\";
		String credsFile = "src\\creds.csv";
		String dataFilename = "src\\CatalogItems.csv";
		
		Catalog theCatalog = new Catalog(dataFilename);
		Logger theLogger = Logger.createLogger(srcPath);
		
		UserLogin userAuth = new UserLogin(theCatalog, credsFile);
		if ( !userAuth.logIn() ) return;
		userAuth.loadUserInfo();
		CartContents userCartContents = userAuth.loadUserCart();
		
		OrderProcessor shopper = new OrderProcessor(userCartContents, theCatalog);

		shopper.shop();
		
		Cart theCart = new Cart.CartBuilder()
				.setCustName(userAuth.getUsername())
				.setCustAddr(userAuth.getCustAddr())
				.setCustCardType(userAuth.getCustCardType())
				.setCustCardNum(userAuth.getCustCardNum())
				.setCartItems(userCartContents)
				.build();
		
		PaymentProcessor payProcess = new PaymentProcessor(theCart);
		payProcess.finalizeOrder();
	}

}
