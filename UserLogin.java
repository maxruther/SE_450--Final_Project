import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserLogin {
	// This string, credsFile, is the filepath for the csv holding 
	// the usernames and passwords.
	private String credsFile;
	
	// The user/pass creds are read from that file into this HashMap,
	// which is later re-saved to that file at the end of the session.
	private HashMap<String, String> credsMap;
	
	// One method of this class, loadUserCart(), loads the cart that a user
	// had at the end of the previous session. It refers to the Catalog to
	// load that object.
	private Catalog theCatalog;
	
	// Following are the fields for customer info.
	private String username;
	private String password;
	private boolean newUser;
	private String custAddr;
	private String custCardType;
	private int custCardNum;

	private Scanner sc;
	
	// The Logger is used to log user log-ins as well as new user registrations
	// as important events, to the importantEvents.csv file.
	private Logger theLogger;
	
	public UserLogin (Catalog theCatalog, String credsFile) {
		this.theCatalog = theCatalog;
		this.credsFile = credsFile;
		
		
		this.credsMap = loadSavedCreds();
		
		// DELETE THIS, MAX
		System.out.println(credsMap);
		
		this.theLogger = Logger.createLogger();
		
		// Customer info fields are initialized to empty strings and zero.
		this.username = "";
		this.custAddr = "";
		this.custCardType = "";
		this.custCardNum = 0;
		
		// This flag is set to true when the user is newly registered.
		// This is useful for the loadUserInfo() method, which loads customer
		// info from "custInfo_<USERNAME>.csv" files if that flag isn't on.
		this.newUser = false;
	}
	
	// The loadSavedCreds() method is the first run.
	// It reads user/password pairs from the credsFile into a HashMap.
	// Newly registered user/pass pairs are added to this HashMap, which is
	// always used to rewrite the credsFile at the end of the program.
	public HashMap<String, String> loadSavedCreds() {
		HashMap<String, String> credsMap = new HashMap<>();
		
		try {
			BufferedReader br;
			String line = "";
			br = new BufferedReader(new FileReader(this.credsFile));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				String user = row[0];
				String pass = row[1];
				
				credsMap.put(user, pass);
				
			}	
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return credsMap;
	}
	
	// The following method is used to log users in. If a username isn't
	// recognized, then it can be registered along with a password.
	// After a user is logged in, after which the user's cart and
	// customer info from previous sessions can be loaded.
	public boolean logIn() {
		// logIn() is boolean because it returns true or false, depending
		// on whether the log-in was successful.
		// A return of false leads to the termination of MainClass.
		
		sc = new Scanner(System.in);
		
		// The user is asked for their username.
		System.out.println("\n---------------------------------\n"
						   + "             LOG-IN\n"
						   + "---------------------------------");
		System.out.println("\nPlease enter your username: ");
		username = sc.nextLine().toLowerCase();
		
		boolean loggedIn = false;
		boolean quitting = false;
		
		// A loop persists until the user has either quit or logged in.
		while (!quitting && !loggedIn) {
			
			// If the entered username matches one from the creds HashMap, they are asked
			// for a password.
			if (credsMap.containsKey(username)) {
				System.out.println("\nPlease enter your password: ");
				
				boolean entryValid = false;
				boolean firstLoop = true;
				String pass_entry = "non-empty";
				
				// Password entry is looped until either a password matches or the user
				// provides a blank password entry, to indicate that they wish to quit.
				while (!entryValid && !pass_entry.isEmpty()) {
					pass_entry = sc.nextLine();
					
					if (pass_entry.equals(credsMap.get(username))) {
						// A matching password results in log-in.
						entryValid = true;
						loggedIn = true;
						System.out.println("\nLog-in successful. Welcome, " + username + ".");
						
						// The successful log-in is logged as an important event.
						theLogger.logEvent(username + " has successfully logged in.");
					} 
					else if (pass_entry.isEmpty() && firstLoop) {
						// If the user provides an empty entry for password before attempting a
						// non-empty one, they are here given another chance to enter a non-empty
						// password, in case it was a mistake.
						
						System.out.println("No password entered. The next empty password entry will quit the program.\n"
								+ "\nIf instead you would like to enter a password, please do so: \n");
						firstLoop = false;
						pass_entry = "non-empty";
					}
					else {
						if (firstLoop || (!pass_entry.isEmpty() && !entryValid)) {
							// A non-matching password results in the loop continuing. Unlimited password attempts are
							// allowed.
							System.out.println("Invalid username and password combination.\n");
							System.out.println("Please try entering the password again, or enter nothing to quit out.");
							firstLoop = false;
						}
						else {
							System.out.println("Empty password entry received. Quitting.");
						}
					}
				}

			}
			else {
				// If a username does not match any existing ones, then the user is given the choice 
				// to register it or quit.
				System.out.println("\nUsername not found. Would you like to register a new one?\n"
						+ "Please indicate (y) or (n):");
				String tempResponse = sc.nextLine().toLowerCase();
				
				// The user's response is here constrained to "y" or "n"
				while(!tempResponse.equals("y") && !tempResponse.equals("n")) {
					System.out.println("Invalid entry: " + tempResponse + "\n\nPlease enter (y) or (n): ");
					tempResponse = sc.nextLine().toLowerCase();
				}
				
				// If the user chooses to register a new username, they must provide a password and
				// successfully retype it to confirm.
				if (tempResponse.equals("y")) {
					
					boolean passesMatch = false;
					while (!passesMatch) {
						System.out.println("\nPlease create a password:");
						String newPass = sc.nextLine();
						System.out.println("\nPlease retype the password to confirm:");
						String newPassConfirm = sc.nextLine();
						
						if (newPass.equals(newPassConfirm)) {
							passesMatch = true;
							System.out.println("\nUser and password registration successful. You are logged in.");
							System.out.println("\nWelcome, " + username + ".");
							
							loggedIn = true;
							newUser = true;
							password = newPass;
							this.credsMap.put(username, password);
							
							// In this updateCreds() method, the HashMap of user/pass pairs overwrites
							// the credential file, to reflect the newly added user & pass.
//							updateCreds();
							theLogger.saveCredentials(credsFile, credsMap);
							theLogger.saveUserInfo(username, custAddr, custCardType, custCardNum);
							
							theLogger.logEvent("New user registered and logged in: " + username);
						}
						else {
							System.out.println("Retyped password does not match the initial entry. Please try again.\n\n");
						}
					}
					
					
				}
				// If the user instead opts not to register the new username, this logIn() method results in a
				// return of false, which ends MainClass.
				else {
					System.out.println("Understood. Quitting.");
					quitting = true;
				}		
			}
		}
		return loggedIn;
	}
	
	// loadUserInfo() is used to load a user's customer info from their corresponding file.
	public void loadUserInfo() {
		// If the user is freshly registered, this method is immediately concludes.
		if (newUser) return;
		
		// Customer info is held in individual files for each registered user, which are
		// stored in the 'src' directory where the Java class files lie.
		// The naming conventions of such files is 'custInfo_<username>.csv'.
		String userInfoFilename = "src\\custInfo_" + username + ".csv";
		
		try {
			BufferedReader br;
			String line = "";
			br = new BufferedReader(new FileReader(userInfoFilename));
			
			// The custInfo file is searched for a line that shows a matching username
			// in the first column.
			//
			// Originally, this method was written to handle a master customer info file
			// that contained all customers' info. For the convenience of overwriting
			// this info when changes occur, the program was later changed to save to
			// individual customer files.
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				String user = row[0];
				
				if (user.equals(username)) {
					custAddr = row[1];
					custCardType = row[2];
					custCardNum = Integer.parseInt(row[3]);
					break;	
				}
			}
			br.close();	
		} catch (IOException e) {
			System.out.println("File '" + userInfoFilename + "' not found.");
			e.printStackTrace();
		}
		return;
	}
	
	
	// Once a customer's info has been loaded successfully, this method is called to
	// load the cart contents from the previous session.
	//
	// Side note: a CartContents object handles cart management, but is distinct from 
	// the main Cart object. It is held by the main Cart object in a 'has-a' relationship.
	public CartContents loadUserCart() {
		String cartLogFilename = "src\\custCart_" + username + ".csv";
		CartContents newCart = new CartContents();
		
		File f = new File(cartLogFilename);
		
		if (!f.exists()) {
			// If custCart file doesn't exist for the user, a blank CartContents object
			// is returned.
			return newCart;
		}
		else {
			// Otherwise, the user's prior cart contents are read in from file.
			try {
				BufferedReader br;
				String line = "";
				br = new BufferedReader(new FileReader(cartLogFilename));
				while ((line = br.readLine()) != null) {
					String[] row = line.split(",");
					
					// The rows of a custCart file are like so: Product ID, Product Qty
					// Product ID is a unique integer key for this data.
					int prodID = Integer.parseInt(row[0]);
					int qty = Integer.parseInt(row[1]);
					
					// The catalog is referenced to add corresponding products to the
					// cart, based on matching ProductID.
					for (int i = 0; i < theCatalog.getSize(); i++) {
						Product p = theCatalog.getItem(i+1);
						if (p.getProdID() == prodID)
							newCart.addToCart(p, qty);
					}
				}
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return newCart;
		}
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustAddr() {
		return custAddr;
	}

	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}

	public String getCustCardType() {
		return custCardType;
	}

	public void setCustCardType(String custCardType) {
		this.custCardType = custCardType;
	}

	public int getCustCardNum() {
		return custCardNum;
	}

	public void setCustCardNum(int custCardNum) {
		this.custCardNum = custCardNum;
	}
	
	
}
