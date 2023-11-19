
public class MainClass {

	public static void main(String[] args) {
		String credsFile = "src\\creds.csv";
		String dataFilename = "src\\CatalogItems.csv";
		
		UserLogin authenty = new UserLogin(credsFile);
		authenty.logIn();
		
		
//		Catalog theCatalog = new Catalog(dataFilename);
//		theCatalog.printCatalog();
//		
//		System.out.println(theCatalog.getCatalogItem(6).getName());
//		
//		
//		Cart theCart = Cart.createCart("Maxwell R");
//		cartContents itemsInCart = theCart.getCartItems();
//		itemsInCart.addToCart(theCatalog.getCatalogItem(6));
//		itemsInCart.addToCart(theCatalog.getCatalogItem(1));
//		itemsInCart.addToCart(theCatalog.getCatalogItem(6));
//		itemsInCart.addToCart(theCatalog.getCatalogItem(3), 3);
//		itemsInCart.printCart();
//		
		
	}

}
