package cn.com.ttblog.jfinal_bootstrap_table.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class POIExcelUtil {
	public static void test() {
		Workbook wb = new HSSFWorkbook(); // or new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet1 = wb.createSheet("new sheet");
		Row row = sheet1.createRow((short) 0);
		// Create a cell and put a value in it.
		Cell cell = row.createCell(0);
		cell.setCellValue(1);

		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue(
				createHelper.createRichTextString("This is a string"));
		row.createCell(3).setCellValue(true);

		Sheet sheet2 = wb.createSheet("second sheet");
		// Note that sheet name is Excel must not exceed 31 characters
		// and must not contain any of the any of the following characters:
		// 0x0000
		// 0x0003
		// colon (:)
		// backslash (\)
		// asterisk (*)
		// question mark (?)
		// forward slash (/)
		// opening square bracket ([)
		// closing square bracket (])

		// You can use
		// org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String
		// nameProposal)}
		// for a safe way to create valid names, this utility replaces invalid
		// characters with a space (' ')
		String safeName = WorkbookUtil
				.createSafeSheetName("[O'Brien's sales*?]"); // returns
		Sheet sheet3 = wb.createSheet(safeName);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("workbook.xls");
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
