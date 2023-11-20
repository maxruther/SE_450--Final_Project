import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	private static Logger instance;
	
	private String srcPath;
	
	private Logger(String srcPath) {
		this.srcPath = "src\\";
	}
	
	public static Logger createLogger() {
		if (instance != null)
			return instance;
		else {
			System.out.println("Logger hasn't yet been created.");
			return null;
		}
	}
	
	public static Logger createLogger(String srcPath) {
		if (instance == null) {
			instance = new Logger(srcPath);
		}
		return instance;
	}
	
	public void saveUserInfo(String custName, String custAddr, String custCardType, int custCardNum) {
		String userInfoFilename = srcPath + "custInfo_" + custName + ".csv";
		try {
			FileWriter fileWriter = new FileWriter(userInfoFilename, false);
			fileWriter.write(custName + "," + custAddr + "," + custCardType + "," + custCardNum + "\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveUserCart(String custName, CartContents cartItems) {
		String userCartFilename = srcPath + "custCart_" + custName + ".csv";
		try {
			FileWriter fileWriter = new FileWriter(userCartFilename, false);
			StringBuilder sr = new StringBuilder();
			
			for (int i = 0; i < cartItems.getVariety(); i++) {
				Product p = cartItems.getItem(i+1);
				sr.append(p.getProdID() + "," + p.getQty() + "\n");
			}
			
			fileWriter.write(sr.toString());
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logUserOrder(Cart theCart) {
		String completedOrderFile = "src\\completedOrders.csv";
		
		String custName = theCart.getCustName();
		CartContents cartItems = theCart.getCartItems();
		
		double totalSale = 0;
		for (int i = 0; i < cartItems.getVariety(); i++) {
			Product p = cartItems.getItem(i+1);
			totalSale = totalSale + (p.getPrice() * p.getQty());
		}
		
		try {
			FileWriter fileWriter = new FileWriter(completedOrderFile, true);
			fileWriter.write(custName + "," + totalSale + "\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logEvent(custName + " completed an order with a total price of $" + totalSale);
	}
	
	public void logEvent(String message) {
		String eventsFilename = "src\\importantEvents.csv";
		
		try {
			FileWriter fileWriter = new FileWriter(eventsFilename, true);
			fileWriter.write(message + "\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
