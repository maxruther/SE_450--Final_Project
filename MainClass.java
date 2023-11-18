
public class MainClass {

	public static void main(String[] args) {
		String dataFilename = "src\\CatalogItems.csv";
		Catalog theCatalog = new Catalog(dataFilename);
		theCatalog.printCatalog();
		
		System.out.println(theCatalog.getCatalogItem(6).getName());
		
		
		Cart theCart = Cart.createCart("Maxwell R");
		theCart.addToCart(theCatalog.getCatalogItem(6));
		theCart.addToCart(theCatalog.getCatalogItem(1));
		theCart.addToCart(theCatalog.getCatalogItem(6));
		theCart.addToCart(theCatalog.getCatalogItem(3), 3);
		theCart.printCart();
		
		
	}

}
