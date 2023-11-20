import java.util.Scanner;

public class PaymentProcessor {
	// The PaymentProcessor has access to both the user's customer info and cart 
	// contents (unlike the OrderProcessor that only has access to cart contents.)
	//
	// The PaymentProcessor handles the final actions of the session, either completing
	// the order's purchase or saving it for future sessions, all according to the 
	// user's choice.
	
	private Cart theCart;
	private Logger theLogger;
	
	private Scanner sc;
	private GetInput getInput;
	
	public PaymentProcessor() {
		// This constructor conveniently doesn't need to take the Cart or Logger
		// objects as arguments, because they are Singleton classes.
		this.theCart = new Cart.CartBuilder().build();
		theLogger = Logger.createLogger();
		
		// A scanner and a GetInput are instantiated. The GetInput provides methods to retrieve
		// varied, valid responses from users.
		sc = new Scanner(System.in);
		getInput = new GetInput(sc);
	}
	
	// finalizeOrder() is essentially the Main method of the PaymentProcessor.
	public void finalizeOrder() {
		if (!theCart.getCartItems().isSaleReady()) {
			// If the user indicated in the OrderProcessor that they would like to quit
			// without making a purchase, the program control gets directed here.
			//
			// In this branch, the cart contents and customer info of the user are saved for
			// the next session.
			System.out.println("\nSaving your cart for your next log-in."
					+ "\n\nThank you for shopping. Come again soon!");
			
			theLogger.saveUserInfo(theCart.getCustName(),
					theCart.getCustAddr(), theCart.getCustCardType(), theCart.getCustCardNum());
			theLogger.saveUserCart(theCart.getCustName(), theCart.getCartItems());
			
			// A user's quitting without making a purchase is logged in the importantEvents.csv file.
			theLogger.logEvent(theCart.getCustName() + " has logged out without completing the purchase.");
			return;
		}
		else {
			// If the user elected to complete their purchase, the program control is directed here.
			
			// The order is printed for the user's review.
			System.out.println(theCart);
			
			// If any of the user's customer info is blank, this method retrieves that info from them.
			getInput.fillInRequiredInfo(theCart);
			
			// With customer info all filled out, the user is asked whether they would like to confirm 
			// the order, save it and quit, or edit any of their customer information before deciding.
			System.out.println("If all of the above information is correct, please enter (y) to confirm order.");
			System.out.println("Please enter (n) if you wish to cancel. (Your cart will be saved.)");
			System.out.println("Please enter (e) if you wish to edit any customer information.");
			
			// The GetInput object retrieves the user's response of "y", "n", or "e".
			String tempResponse = getInput.userSaysY_N_E();
			
			while(tempResponse.equals("e")) {
				// The user is shown the customer info fields, for them to select for editing.
				System.out.println("\nWhich information would you like to edit?\n");
				System.out.println("1) ADDRESS: " + theCart.getCustAddr());
				System.out.println("2) CARD TYPE: " + theCart.getCustCardType());
				System.out.println("3) CARD NUMBER: " + theCart.getCustCardNum());
				System.out.println("\nEnter (4) if you no longer wish to edit information.");
				
				// The GetInput object retrieves a valid menu choice from the user.
				int userChoice = getInput.userSelectsChoice(4);
				
				// The user enters a new string for whichever customer info field they selected.
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
				
				// A choice of (4) is how one leaves the editing menu.
				if (userChoice == 4) {
					System.out.println("Would you like to finalize your order now (y), quit (n), or edit info (e) ?");
					System.out.println("(If you quit, your cart will be saved.)");
					tempResponse = getInput.userSaysY_N_E();
				}	
			}
			
			if (tempResponse.equals("n")) {
				// A response of "n" from the user indicates that they wish to quit without purchasing.
				System.out.println("\nCancelling and saving your cart. Come again soon!");
				
				// Their cart and customer info are saved by the Logger.
				theLogger.saveUserCart(theCart.getCustName(),
										theCart.getCartItems());
				
				theLogger.saveUserInfo(theCart.getCustName(),
										theCart.getCustAddr(),
										theCart.getCustCardType(),
										theCart.getCustCardNum());
				
				// The Logger also records in the 'importantEvents.csv' file that the user has quit without purchasing.
				theLogger.logEvent(theCart.getCustName() + " has logged out without completing the purchase.");
				return;
			}
			
			if (tempResponse.equals("y")) {
				// A response of "y" from the user indicates that they wish to complete the purchase.
				System.out.println("\nOrder confirmed. Thank you for your purchase!");
				
				// The completed order is recorded by the Logger in the 'completedOrders.csv'.
				// Each entry therein records the username and total sale amount.
				theLogger.logUserOrder(theCart);
				
				// The user's customer info is saved.
				theLogger.saveUserInfo(theCart.getCustName(),
										theCart.getCustAddr(),
										theCart.getCustCardType(),
										theCart.getCustCardNum());
				
				// The user's cart is cleared, then saved to file (as empty.)
				theCart.clearCartContents();
				theLogger.saveUserCart(theCart.getCustName(), theCart.getCartItems());
				
				return;
			}
		}
	}
	
}