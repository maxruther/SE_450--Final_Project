import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

class CartContentsTests {

	@Test
	void testRemoveMoreThanPresent() {
		CartContents testCart = new CartContents();
		
		String catalogFile = "src\\CatalogItems.csv";
		Catalog theCatalog = new Catalog(catalogFile);
		Product testProd = theCatalog.getItem(1);
		
		boolean testFailed = false;
		
		for (int i = 0; i < 10; i++) {
			int qty1 = getRandomInt(1, 1000000);
			int qty2 = getRandomInt(qty1 + 1, 2000000);
			
			testCart = new CartContents();
			testCart.addToCart(testProd, qty1);
			testCart.removeFromCart(testProd, qty2);
			
//			Product cartProd = testCart.getItem(1);
			ArrayList<Product> testCartContents = testCart.getCartItems();
			
			for (int k = 0; k < testCartContents.size(); k++) {
				Product p = testCartContents.get(k);
				if ( p.getProdID() == testProd.getProdID() )
					testFailed = true;
			}
			
			System.out.println("Add qty: " + qty1 + "\t\tRmv qty: " + qty2 
								+ "\t\t\tAddQty-RmvQty : " + (qty1-qty2) + "\t\t\tTest failed: " + testFailed);
		}
		
		assertFalse(testFailed);
		
	}
	
	public int getRandomInt(int min, int max) {
	    Random random = new Random();
	    return random.nextInt(max - min) + min;
	}

}
