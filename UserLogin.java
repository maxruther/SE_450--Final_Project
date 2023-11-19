import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class UserLogin {
	private String credsFile;
	private HashMap<String, String> credsMap;
	private String username;
	private String password;

	private Scanner sc;
	
	public UserLogin (String credsFile) {
		this.credsFile = credsFile;
		this.credsMap = loadSavedCreds();
		System.out.println(credsMap);
	}
		
	public void logIn() {
		sc = new Scanner(System.in);
		System.out.println("Please enter your username: \n");
		username = sc.nextLine().toLowerCase();
		
		boolean loggedIn = false;
		boolean quitting = false;
		
		while (!quitting && !loggedIn) {
			
			if (credsMap.containsKey(username)) {
				System.out.println("Please enter your password: \n");
				
				boolean entryValid = false;
				boolean firstLoop = true;
				String pass_entry = "non-empty";
				while (!entryValid && !pass_entry.isEmpty()) {
					pass_entry = sc.nextLine();
					if (pass_entry.equals(credsMap.get(username))) {
							entryValid = true;
							loggedIn = true;
							System.out.println("Valid username and pass combination.\n\n" + "Welcome, " + username);
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
					System.out.println("Please create a password:\n");
					String newPass = sc.nextLine();
					System.out.println("Please retype the password to confirm:\n");
					String newPassConfirm = sc.nextLine();
					
					if (newPass.equals(newPassConfirm)) {
						System.out.println("User and password registrations successful. You are logged in.\n");
						System.out.println("Welcome, " + username);
						
						loggedIn = true;
						password = newPass;
						this.credsMap.put(username, password);
						updateCreds();
					}
					
				}
				else {
					System.out.println("Understood. Quitting.");
					quitting = true;
				}
				
			}
		}
		
		
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
	
	public void updateCreds() {
//		try {
//			FileWriter fileWriter = new FileWriter("src\\credsTEST.csv", false);
//
//			for (Entry<String, String> creds : credsMap.entrySet()) {
//				String user = creds.getKey();
//				String pass = creds.getValue();
//				
//				fileWriter.write(user + "," + pass + "\n");
//			}
//			
//			fileWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
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
}
