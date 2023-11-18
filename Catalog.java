import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Catalog {
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
						row[0], Double.valueOf(row[1]));
				
				size++;
				newProd.setProductNum(size);
				catalogItems.add(newProd);
				
			}	
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Product> getCatalogItems() {
		return catalogItems;
	}

	public void setCatalogItems(ArrayList<Product> catalogItems) {
		this.catalogItems = catalogItems;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public Product getCatalogItem(int itemNo) {
		Product requestedItem = catalogItems.get(itemNo - 1);
//		return prodFact.createProduct(requestedItem.getType(),
//									  requestedItem.getName(), 
//									  requestedItem.getPrice());
		return requestedItem;
	}

	public void printCatalog() {
		System.out.println("AVAILABLE FOR PURCHASE");
		System.out.println("----------------------\n");
		
		for (Product p: catalogItems) {
			System.out.println(p.getProductNum() + ")");
			System.out.println(p.toString() + "\n");
		}
	}
}
