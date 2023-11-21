
public class MainClass {

	public static void main(String[] args) {
		// The filepaths for the credentials and product catalog are here hardcoded.
		String credsFile = "src\\creds.csv";
		String catalogFile = "src\\CatalogItems.csv";
		
		// A Catalog object is created, by reading from the catalog data file.
		Catalog theCatalog = new Catalog(catalogFile);
		
		// A UserLogin object is created, using this Catalog and the credentials file.
		UserLogin userAuth = new UserLogin(theCatalog, credsFile);
		
		// Log-in takes place in this next line. If the user doesn't successfully log 
		// in, the program terminates.
		if ( !userAuth.logIn() ) return;
		
		// However, if user log-in is indeed successful, then that user's customer info, 
		// if ever entered prior, is loaded from a file.
		//
		// This UserLogin class is what houses the customer information, at least until
		// the Cart is built later for order finalization.
		
		
		// In addition to loading the user's customer info, the UserLogin object
		// also loads the cart contents from the previous session.
		//
		// It loads them into a CartContents objects, which largely handles cart 
		// management and is later held by the main Cart object in a "has-a" relationship.
		CartContents userCartContents = userAuth.loadUserCart();
		
		// The OrderProcessor executes the shopping experience for the user. It does so
		// by using the user's CartContents and the Catalog. It does not need customer
		// information.
		OrderProcessor shopper = new OrderProcessor(userCartContents, theCatalog);
		shopper.shop();
		
		// Once the shopping is complete, cart contents and customer info are used to
		// construct the Cart object through a builder pattern. The Cart object is also
		// Singleton.
		//
		// The Cart in this program essentially represents the final order, though it
		// can also represent an order that should be saved rather than completed this
		// session.
		Cart theCart = new Cart.CartBuilder()
				.setCustName(userAuth.getUsername())
				.setCustAddr(userAuth.getCustAddr())
				.setCustCardType(userAuth.getCustCardType())
				.setCustCardNum(userAuth.getCustCardNum())
				.setCartItems(userCartContents)
				.build();
		
		// The PaymentProcessor uses the Cart to finalize the order, requesting customer
		// billing information if it's missing. 
		//
		// The Cart is a public Singleton, so it
		// doesn't get passed to PaymentProcessor directly. It's instead "created" by
		// the PaymentProcessor's constructor.
		PaymentProcessor payProcess = new PaymentProcessor();
		payProcess.finalizeOrder();
	}

}
