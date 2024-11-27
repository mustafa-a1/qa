package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public FileInputStream fis;
	public XSSFWorkbook wb;
	public XSSFSheet sh;
	public XSSFRow row;
	public XSSFCell cell;
	String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(sheetName);
		int rowCount = sh.getLastRowNum();
		wb.close();
		fis.close();
		return rowCount;
	}

	public int getCellCount(String sheetName, int rownum) throws IOException {

		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(sheetName);
		row = sh.getRow(rownum);
		int cellCount = row.getLastCellNum();
		wb.close();
		fis.close();
		return cellCount;

	}

	public String getCellData(String sheetName, int rownum, int column) throws IOException {

		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(sheetName);
		row = sh.getRow(rownum);
		cell = row.getCell(column);

		// Will return the formatted cell value as a string regardless of the cell value
		DataFormatter formatter = new DataFormatter();
		String data;

		try {
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fis.close();
		return data;

	}

}
