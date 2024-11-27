package roughWork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadingDataFromExcel {

	public static void main(String[] args) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\testData\\testData.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheet("Sheet1");

		int totalRow = sh.getLastRowNum();
		int totalCells = sh.getRow(0).getLastCellNum();

		for (int i = 0; i <= totalRow; i++) {
			XSSFRow row = sh.getRow(i);
			for (int j = 0; j < totalCells; j++) {
				XSSFCell cell = row.getCell(j);
				System.out.print(cell.toString() + " ");
			}
			System.out.println();
		}

		wb.close();
		fis.close();

	}

}
