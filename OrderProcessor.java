import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderProcessor {
	private CartContents usersCart;
	private Catalog catalog;
	private int menuLength = 5;
	
	private Scanner sc;
	
	public OrderProcessor(CartContents usersCart, Catalog catalog) {
		this.usersCart = usersCart;
		this.catalog = catalog;
		this.sc = new Scanner(System.in);
	}
	
	public CartContents shop() {
		printMenu();
		int userSelect = userSelectsChoice(menuLength);
		
		while (userSelect != menuLength) {
			
			if (userSelect == 1) {
				if (usersCart.getVariety() == 0) System.out.println("\n\nYour cart is empty. Please feel free to add items.");
				else usersCart.printCart();
			}
			else if (userSelect == 2) {
				addCartContents();
			}
			else if (userSelect == 3) {
				if (usersCart.getVariety() == 0) {
					System.out.println("\nSorry, you cannot remove items when your cart is already empty.\n");
					System.out.println("Please select a different option:");
				} else {
					removeCartContents();
				}
			}
			else if (userSelect == 4) {
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
			userSelect = userSelectsChoice(menuLength);
			
		}
		return usersCart;
	}
	
	public void printMenu() {
		System.out.println("\n\n--------------- MAIN MENU ---------------");
		System.out.println(  "\nPlease choose from the available options:\n");
		System.out.println("1. View contents of cart.");
		System.out.println("2. Add products to cart.");
		System.out.println("3. Remove products from cart.");
		System.out.println("4. Finalize order and pay.");
		System.out.println("5. Quit. (Your cart will be saved.)");
		
	}
	
	public void viewCartContents() {
		usersCart.printCart();
	}
	
	public void addCartContents() {
		catalog.printCatalog();
		System.out.println("\n\nTo choose a listed item, please enter its corresponding"
							+ " number. Or enter 0 to quit:\n");
		
		int prodChoice = userSelectsItem(catalog);
		
		if (prodChoice > 0) {
			System.out.println("How many would you like?");
			int qtyChoice = userIndicatesQty();
			
			usersCart.addToCart(catalog.getItem(prodChoice), qtyChoice);
		}
	}
	
	public void removeCartContents() {
		usersCart.printCart();
		System.out.println("\n\nTo choose a cart item to remove, please enter its corresponding number. Or enter 0 to quit:\n");
		int prodChoice = userSelectsItem(usersCart);
		
		if (prodChoice > 0) {
			System.out.println("How many would you like to remove?");
			int qtyChoice = userIndicatesQty();
			
			usersCart.removeFromCart(usersCart.getItem(prodChoice), qtyChoice);
		}
	}
	
	public int userSelectsChoice(int numChoices) {
		int userInput = 0;
		while (true) {
			try {
				userInput = sc.nextInt();
				sc.nextLine();
				while ( !(userInput >= 1 && userInput <= numChoices) ) {
					System.out.println("Invalid choice entered. Please enter an integer between 1 and " + numChoices + ".");
					userInput = sc.nextInt();
					sc.nextLine();
				}
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Invalid input. Please input an integer.");
			}
		}
		
		return userInput;
	}
	
	public int userSelectsItem(ProductList pl) {
		int userInput = 0;
		while (true) {
			try {
				userInput = sc.nextInt();
				sc.nextLine();
				while ( !(userInput >= 0 && userInput <= pl.getVariety()) ) {
					System.out.println("Invalid choice entered. Please enter integer between 0 and " + pl.getVariety() + ".");
					userInput = sc.nextInt();
					sc.nextLine();
				}
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Invalid input. Please input an integer.");
			}
		}
		if (userInput > 0) {
			Product tempProd = pl.getItem(userInput);
			System.out.println("Product selected: " + tempProd.getName() + " || $" + tempProd.getPrice());
			if (pl instanceof CartContents) System.out.println("Qty in cart: " + tempProd.getQty() + "\n");
			
		}
		return userInput;
	}
	
	public int userIndicatesQty() {
		int userInput = 0;
		while (true) {
			try {
				userInput = sc.nextInt();
				sc.nextLine();
				while ( !(userInput >= 0 ) ) {
					System.out.println("Invalid choice entered. Please enter an integer greater than zero.");
					userInput = sc.nextInt();
					sc.nextLine();
				}
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Invalid input. Please input an integer.");
			}
		}
		
		return userInput;
	}
}
