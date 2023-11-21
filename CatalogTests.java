import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CatalogTests {

	@Test
	void testCatalogVariety() {
		// Testing whether the variety in the catalog matches the number of distinct productID's
		// from the catalog data file.
		// I wrote in the rows of the catalog data file to feature unique products. If I were to
		// mistakenly add a non-unique product to that file, this test could help me catch it.
		String catalogFile = "src\\CatalogItems.csv";
		Catalog theCatalog = new Catalog(catalogFile);
		
		ArrayList<String> typesFromFile = new ArrayList<String>();
		
		String line = "";
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(catalogFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				String productID = row[3];
				
				if ( !typesFromFile.contains(productID) ) {
					typesFromFile.add(productID);
				}
				
			}	
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println(typesFromFile.size());
//		System.out.println(theCatalog.getVariety());
		assertTrue(typesFromFile.size() == theCatalog.getVariety());
	}

}
