import java.util.InputMismatchException;
import java.util.Scanner;

public class PaymentProcessor {
	private Cart theCart;
	private Scanner sc;
	private Logger theLogger;
	
	public PaymentProcessor(Cart theCart) {
		this.theCart = theCart;
		sc = new Scanner(System.in);
		theLogger = Logger.createLogger();
	}
	
	public void finalizeOrder() {
		if (!theCart.getCartItems().isSaleReady()) {
			System.out.println("\nSaving your cart for your next log-in."
					+ "\n\nThank you for shopping. Come again soon!");
			theLogger.saveUserInfo(theCart.getCustName(),
					theCart.getCustAddr(), theCart.getCustCardType(), theCart.getCustCardNum());
			theLogger.saveUserCart(theCart.getCustName(), theCart.getCartItems());
			theLogger.logEvent(theCart.getCustName() + " has logged out without completing the purchase.");
			return;
		}
		else {
			System.out.println(theCart);
			
			fillInRequiredInfo();
			
			System.out.println("If all of the above information is correct, please enter (y) to confirm order.");
			System.out.println("Please enter (n) if you wish to cancel. (Your cart will be saved.)");
			System.out.println("Please enter (e) if you wish to edit any customer information.");
			
			String tempResponse = sc.nextLine().toLowerCase();

			while(!tempResponse.equals("y") && !tempResponse.equals("n") && !tempResponse.equals("e")) {
				System.out.println("Invalid entry: " + tempResponse + "\n\nPlease enter (y), (n) or (e): ");
				tempResponse = sc.nextLine().toLowerCase();
			}
			
			while(tempResponse.equals("e")) {
				System.out.println("\nWhich information would you like to edit?\n");
				System.out.println("1) ADDRESS: " + theCart.getCustAddr());
				System.out.println("2) CARD TYPE: " + theCart.getCustCardType());
				System.out.println("3) CARD NUMBER: " + theCart.getCustCardNum());
				System.out.println("\nEnter (4) if you no longer wish to edit information.");
				
				int userChoice = userSelectsChoice(4);
				
				if (userChoice == 1) {
					System.out.println("Please enter the new address: \n");
					theCart.setCustAddr(sc.nextLine());
				}
				else if (userChoice == 2) {
					System.out.println("Please enter the new card type: \n");
					theCart.setCustCardType(sc.nextLine());
				}
				else if (userChoice == 3) {
					System.out.println("Please enter the new card number (just 4 digits please): ");
					theCart.setCustCardNum(sc.nextInt());
					sc.nextLine();
				}
				
				if (userChoice == 4) {
					System.out.println("Would you like to finalize your order now (y), quit (n), or edit info (e) ?");
					System.out.println("(If you quit, your cart will be saved.)");
					
					tempResponse = sc.nextLine().toLowerCase();
					while(!tempResponse.equals("y") && !tempResponse.equals("n") && !tempResponse.equals("e")) {
						System.out.println("Invalid entry: " + tempResponse + "\n\nPlease enter (y), (n) or (e): ");
						tempResponse = sc.nextLine().toLowerCase();
					}
				}	
			}
			
			if (tempResponse.equals("n")) {
				System.out.println("\nCancelling and saving your cart. Come again soon!");
				theLogger.saveUserCart(theCart.getCustName(),
										theCart.getCartItems());
				
				theLogger.saveUserInfo(theCart.getCustName(),
										theCart.getCustAddr(),
										theCart.getCustCardType(),
										theCart.getCustCardNum());
				theLogger.logEvent(theCart.getCustName() + " has logged out without completing the purchase.");
				return;
			}
			
			if (tempResponse.equals("y")) {
				System.out.println("\nOrder confirmed. Thank you for your purchase!");
				// Logging the user's order, in the completedOrders.csv and in the importantEvents.csv
				theLogger.logUserOrder(theCart);
				
				theLogger.saveUserInfo(theCart.getCustName(),
						theCart.getCustAddr(), theCart.getCustCardType(), theCart.getCustCardNum());
				
				// Clearing user's cart then saving it to file.
				theCart.clearCartContents();
				theLogger.saveUserCart(theCart.getCustName(), theCart.getCartItems());
				
				return;
				
			}
		}
	}
	
	private void fillInRequiredInfo() {
		boolean emptyAddr = theCart.getCustAddr().isEmpty();
		boolean emptyCardType = theCart.getCustCardType().isEmpty();
		boolean emptyCardNum = (theCart.getCustCardNum()==0);
		if (emptyAddr || emptyCardType || emptyCardNum ) {
			System.out.println("----------------\nBILLING INFO NEEDED\n----------------\n");
			System.out.println("Before you can complete your order, you must enter some billing information:.\n");
			
			if (emptyAddr) {
				System.out.println("Please enter an address: \n");
				theCart.setCustAddr(sc.nextLine());
			}
			
			if (emptyCardType) {
				System.out.println("Please enter your credit card type (eg. Visa, MasterCard, ...) :");
				theCart.setCustCardType(sc.nextLine());
			}
			
			if (emptyCardNum) {
				System.out.println("Please enter your number (just 4 digits please): ");
				theCart.setCustCardNum(sc.nextInt());
				sc.nextLine();
			}
		}
		
		System.out.println("\nThank you for entering that information. Please review your order below.\n");
		System.out.println(theCart);
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
}