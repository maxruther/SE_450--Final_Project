import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Catalog implements ProductList{
	private ArrayList<Product> catalogItems;
	private int size;
	private ProductFactory prodFact;
	
	public Catalog(String filepath) {
		size = 0;
		catalogItems = new ArrayList<Product>();
		prodFact = new ProductFactory();
		String line = "";
		
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(filepath));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				Product newProd = prodFact.createProduct(row[2],
						row[0], Double.valueOf(row[1]), Integer.parseInt(row[3]));
				
				size++;
				newProd.setProductNum(size);
				catalogItems.add(newProd);
				
			}	
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int getSize() {
		return this.size;
	}
	
	public Product getItem(int itemNo) {
		Product requestedItem = catalogItems.get(itemNo - 1);
		return requestedItem;
	}
	
	@Override
	public void printProdList() {
		System.out.println("\nAVAILABLE FOR PURCHASE");
		System.out.println("----------------------");
		
		for (Product p: catalogItems) {
			System.out.println();
			System.out.println(p.getProductNum() + ")  " + p.toString());
		}
	}

	@Override
	public int getVariety() {
		return this.getSize();
	}
}
