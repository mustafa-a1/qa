package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {

		// Define path to Excel File
		String path = "./testData/loginData.xlsx";
		ExcelUtility xlutility = new ExcelUtility(path);

		// Get the total number of rows and cells
		int totalRows = xlutility.getRowCount("Sheet1");
		int totalCells = xlutility.getCellCount("Sheet1", 1);

		// Create a table to store login data
		String loginData[][] = new String[totalRows][totalCells];

		// Reading data from the excel file into the table
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < totalCells; j++) {
				loginData[i][j] = xlutility.getCellData("Sheet1", i + 1, j);
			}
		}

		return loginData;

	}

}
