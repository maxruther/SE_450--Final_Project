import java.util.InputMismatchException;
import java.util.Scanner;

public class GetInput {
	private Scanner sc;
	
	public GetInput(Scanner sc) {
		this.sc = sc;
	}
	
	public GetInput() {
		this.sc = new Scanner(System.in);
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
	
	// This userSelectsItem() method is used to retrieve the ProductList index of a specified
	// product. ProductList is the interface implemented by CartContents and Catalog.
	public int userSelectsItem(ProductList pl) {
		int userInput = 0;
		while (true) {
			try {
				userInput = sc.nextInt();
				sc.nextLine();
				while ( !(userInput >= 0 && userInput <= pl.getVariety()) ) {
					System.out.println("Invalid choice entered. Please enter integer between 0 and "
										+ pl.getVariety() + ".");
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
	
	// userIndicatesQty() gets a valid entry for product quantity from the user (a non-negative integer.)
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
	
	public String userSaysY_N() {
		String tempResponse = sc.nextLine().toLowerCase();
		
		// The user's response is here constrained to "y" or "n"
		while(!tempResponse.equals("y") && !tempResponse.equals("n")) {
			System.out.println("Invalid entry: " + tempResponse + "\n\nPlease enter (y) or (n): ");
			tempResponse = sc.nextLine().toLowerCase();
		}
		return tempResponse;
	}
	
	public String userSaysY_N_E() {
		String tempResponse = sc.nextLine().toLowerCase();
		while(!tempResponse.equals("y") && !tempResponse.equals("n") && !tempResponse.equals("e")) {
			System.out.println("Invalid entry: " + tempResponse + "\n\nPlease enter (y), (n) or (e): ");
			tempResponse = sc.nextLine().toLowerCase();
		}
		return tempResponse;
	}
	
	public void fillInRequiredInfo(Cart theCart) {
		boolean emptyAddr = theCart.getCustAddr().isEmpty();
		boolean emptyCardType = theCart.getCustCardType().isEmpty();
		boolean emptyCardNum = (theCart.getCustCardNum()==0);
		if (emptyAddr || emptyCardType || emptyCardNum ) {
			System.out.println("----------------\nBILLING INFO NEEDED\n----------------\n");
			System.out.println("Before you can complete your order, you must enter some billing information:.\n");
			
			if (emptyAddr) {
				System.out.println("Please enter an address:");
				theCart.setCustAddr(sc.nextLine());
			}
			
			if (emptyCardType) {
				System.out.println("\nPlease enter your credit card type (eg. Visa, MasterCard, ...) :");
				theCart.setCustCardType(sc.nextLine());
			}
			
			if (emptyCardNum) {
				System.out.println("\nPlease enter your number (just 4 digits please): ");
				theCart.setCustCardNum(sc.nextInt());
				sc.nextLine();
			}
		}
		
		System.out.println("\nThank you for entering that information. Please review your order below.\n");
		System.out.println(theCart);
	}
	
	
}
