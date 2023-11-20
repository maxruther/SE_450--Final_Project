import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class UserLogin {
	private String credsFile;
	private HashMap<String, String> credsMap;
	private Catalog theCatalog;
	
	private String username;
	private String password;
	private boolean newUser;
	private String custAddr;
	private String custCardType;
	private int custCardNum;

	private Scanner sc;
	private Logger theLogger;
	
	public UserLogin (Catalog theCatalog, String credsFile) {
		this.theCatalog = theCatalog;
		this.credsFile = credsFile;
		this.credsMap = loadSavedCreds();
		
		// DELETE THIS, MAX
		System.out.println(credsMap);
		
		this.theLogger = Logger.createLogger();
		
		this.username = "";
		this.custAddr = "";
		this.custCardType = "";
		this.custCardNum = 0;
		this.newUser = false;
	}
	
	public boolean loadUserInfo() {
		if (newUser) return false;
		
		String userInfoFilename = "src\\custInfo_" + username + ".csv";
		boolean foundInfo = false;
		try {
			BufferedReader br;
			String line = "";
			br = new BufferedReader(new FileReader(userInfoFilename));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				String user = row[0];
				
				if (user.equals(username)) {
					custAddr = row[1];
					custCardType = row[2];
					custCardNum = Integer.parseInt(row[3]);
					foundInfo = true;
					break;	
				}
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return foundInfo;
	}
		
	public boolean logIn() {
		sc = new Scanner(System.in);
		System.out.println("\nPlease enter your username: ");
		username = sc.nextLine().toLowerCase();
		
		boolean loggedIn = false;
		boolean quitting = false;
		
		while (!quitting && !loggedIn) {
			
			if (credsMap.containsKey(username)) {
				System.out.println("\nPlease enter your password: ");
				
				boolean entryValid = false;
				boolean firstLoop = true;
				String pass_entry = "non-empty";
				while (!entryValid && !pass_entry.isEmpty()) {
					pass_entry = sc.nextLine();
					if (pass_entry.equals(credsMap.get(username))) {
							entryValid = true;
							loggedIn = true;
							System.out.println("\nLog-in successful. Welcome, " + username + ".");
							
							theLogger.logEvent(username + " has successfully logged in.");
					} 
					else if (pass_entry.isEmpty() && firstLoop) {
						System.out.println("No password entered. The next empty password entry will quit the program.\n"
								+ "\nIf instead you would like to enter a password, please do so: \n");
						firstLoop = false;
						pass_entry = "non-empty";
					}
					else {
						if (firstLoop || (!pass_entry.isEmpty() && !entryValid)) {
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
				System.out.println("Username not found. Would you like to register a new one?\nPlease indicate (y) or (n):\n");
				String tempResponse = sc.nextLine().toLowerCase();
				
				while(!tempResponse.equals("y") && !tempResponse.equals("n")) {
					System.out.println("Invalid entry: " + tempResponse + "\n\nPlease enter (y) or (n): ");
					tempResponse = sc.nextLine().toLowerCase();
				}
				
				if (tempResponse.equals("y")) {
					
					boolean passesMatch = false;
					while (!passesMatch) {
						System.out.println("Please create a password:\n");
						String newPass = sc.nextLine();
						System.out.println("Please retype the password to confirm:\n");
						String newPassConfirm = sc.nextLine();
						
						if (newPass.equals(newPassConfirm)) {
							passesMatch = true;
							System.out.println("User and password registration successful. You are logged in.\n");
							System.out.println("Welcome, " + username + ".");
							
							loggedIn = true;
							newUser = true;
							password = newPass;
							this.credsMap.put(username, password);
							updateCreds();
							theLogger.logEvent("New user registered: " + username);
						}
						else {
							System.out.println("Retyped password does not match the initial entry. Please try again.\n\n");
						}
					}
					
					
				}
				else {
					System.out.println("Understood. Quitting.");
					quitting = true;
				}
				
			}
		}
		
		return loggedIn;
	}
	
	
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
	
	public CartContents loadUserCart() {
		String cartLogFilename = "src\\custCart_" + username + ".csv";
		CartContents newCart = new CartContents();
		
		File f = new File(cartLogFilename);
		
		if (!f.exists()) {
			return newCart;
		}
		else {
			try {
				BufferedReader br;
				String line = "";
				br = new BufferedReader(new FileReader(cartLogFilename));
				while ((line = br.readLine()) != null) {
					String[] row = line.split(",");
					int prodID = Integer.parseInt(row[0]);
					int qty = Integer.parseInt(row[1]);
					
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
	
	public void updateCreds() {
		
		try {
			FileWriter fileWriter = new FileWriter(this.credsFile, false);

			for (Entry<String, String> creds : credsMap.entrySet()) {
				String user = creds.getKey();
				String pass = creds.getValue();
				
				fileWriter.write(user + "," + pass + "\n");
			}
			
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
