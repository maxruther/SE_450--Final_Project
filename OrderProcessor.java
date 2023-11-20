import java.util.Scanner;

public class OrderProcessor {
	private CartContents usersCart;
	private Catalog catalog;
	private int menuLength = 5;
	
	private Scanner sc;
	private GetInput getInput;
	
	public OrderProcessor(CartContents usersCart, Catalog catalog) {
		this.usersCart = usersCart;
		this.catalog = catalog;
		
		this.sc = new Scanner(System.in);
		getInput = new GetInput(sc);
	}
	
	// This shop() method is essentially the Main method of this OrderProcessor class.
	public CartContents shop() {
		
		// The menu is printed and the user enters a menu choice.
		printMenu();
		int userSelect = getInput.userSelectsChoice(menuLength);
		
		// Depending on the user's menu choice, items are added or removed from the cart, or
		// the user can quit the shopping experience (while indicating whether they are
		// purchasing or not.)
		//
		// When the user's entered integer is equal to the length of the menu, that signifies
		// that they are electing to quit without making a purchase.
		while (userSelect != menuLength) {
			if (userSelect == 1) {
				// This branch handles printing the cart's contents for the user to view.
				// If the cart is empty, a message is printed to clarify that.
				if (usersCart.getVariety() == 0) System.out.println("\n\nYour cart is empty. Please "
																	+ "feel free to add items.");
				else usersCart.printProdList();
			}
			else if (userSelect == 2) {
				// This branch is for adding products to the cart, which is handled by the below method.
				addCartContents();
			}
			else if (userSelect == 3) {
				// This branch is for removing products from the cart, which is handled by removeCartContents().
				//
				// If the cart is empty, a message is printed to clarify that and redirect the user to the menu.
				if (usersCart.getVariety() == 0) {
					System.out.println("\nSorry, you cannot remove items when your cart is already empty.\n");
					System.out.println("Please select a different option:");
				} else {
					removeCartContents();
				}
			}
			else if (userSelect == 4) {
				// This branch handles proceeding to check-out with initial intent to buy (the user will review
				// the order during the subsequent PaymentProcessor's operation.
				//
				// If the cart is empty, a message is printed to clarify that and redirect the user to the menu.
				if (usersCart.getVariety() == 0) {
					System.out.println("\nSorry, you cannot check out when your cart is empty.\n");
					System.out.println("Please select a different option:");
					
				} else {
					System.out.println("Proceeding to checkout.\n\n");
					usersCart.setSaleReady(true);
					break;
				}
			}
			
			printMenu();
			userSelect = getInput.userSelectsChoice(menuLength);
			
		}
		return usersCart;
	}
	
	// This method is employed by shop() to print the main menu.
	public void printMenu() {
		System.out.println("\n\n--------------- MAIN MENU ---------------");
		System.out.println(  "\nPlease choose from the available options:\n");
		System.out.println("1. View contents of cart.");
		System.out.println("2. Add products to cart.");
		System.out.println("3. Remove products from cart.");
		System.out.println("4. Finalize order and pay.");
		System.out.println("5. Quit. (Your cart will be saved.)");
		
	}
	
	// This method handles selecting products from the catalog to add to the cart.
	public void addCartContents() {
		// The catalog is printed, for the user to view the available products.
		catalog.printProdList();
		System.out.println("\n\nTo choose a listed item, please enter its corresponding"
							+ " number. Or enter 0 to quit:\n");
		
		// The user indicates a product choice. Their entry is gathered and validated by
		// the userSelectsItem() method.
		int prodChoice = getInput.userSelectsItem(catalog);
		
		// If the user hasn't entered zero to cancel their decision to add a product,
		// then they are asked to indicate their desired quantity of that product.
		if (prodChoice > 0) {
			System.out.println("How many would you like?");
			
			// The user's entry for product quantity is gathered and validated, by a 
			// dedicated method.
			int qtyChoice = getInput.userIndicatesQty();
			
			// The product is added to the user's cart at the desired quantity.
			usersCart.addToCart(catalog.getItem(prodChoice), qtyChoice);
		}
	}
	
	// This method has the user select products from the cart to remove.
	public void removeCartContents() {
		// The cart is printed for the user to view.
		usersCart.printProdList();
		System.out.println("\n\nTo choose a cart item to remove, please enter its "
						+ "corresponding number. Or enter 0 to quit:\n");
		
		// The user indicates a product choice. Their entry is gathered and validated by
		// the userSelectsItem() method.
		int prodChoice = getInput.userSelectsItem(usersCart);
		
		// If the user hasn't entered zero to cancel their decision to remove a product,
		// then they are asked to indicate the quantity of product to remove.
		if (prodChoice > 0) {
			System.out.println("How many would you like to remove?");
			int qtyChoice = getInput.userIndicatesQty();
			
			// The specified quantity of that product is removed from the user's cart.
			usersCart.removeFromCart(usersCart.getItem(prodChoice), qtyChoice);
		}
	}
	
}
