import java.util.ArrayList;

public class cartContents {
	private ArrayList<Product> prodList;
	
	public void add(Product prod) {
		prodList.add(prod);
	}
	
	public void add(Product prod, int num) {
		for (int i = 0; i < num; i++) prodList.add(prod);
	}
	
	public void remove(Product prod) {
		prodList.remove(prod);
	}
}
