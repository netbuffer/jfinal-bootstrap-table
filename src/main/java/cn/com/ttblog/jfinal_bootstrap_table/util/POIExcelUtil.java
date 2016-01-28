package cn.com.ttblog.jfinal_bootstrap_table.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

public class POIExcelUtil {
	public static void exec(String path) {
		Workbook wb = new HSSFWorkbook(); // or new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("用户信息");
		// 冻结该行，使其无法移动
		sheet.createFreezePane(0, 1, 0, 1);
		// Header header = sheet1.getHeader();
		// header.setCenter("Center Header");
		// header.setLeft("Left Header");
		// header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
		// HSSFHeader.fontSize((short) 16) +
		// "Right w/ Stencil-Normal Italic font and size 16");
		Row row = sheet.createRow((short) 0);
		row.setHeightInPoints(30);
		Cell cell = row.createCell(0);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cell.setCellValue("用户昵称");
		cell.setCellStyle(cellStyle);
		row.createCell(1).setCellValue("用户性别");

		CellStyle hlink_style = wb.createCellStyle();
		Font hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);
		hlink_style.setAlignment(CellStyle.ALIGN_CENTER);
		hlink_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
		link.setAddress("http://poi.apache.org/");
		cell.setHyperlink(link);
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.BORDER_TOP, CellStyle.BORDER_MEDIUM);
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.BORDER_BOTTOM, CellStyle.BORDER_MEDIUM);
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.BORDER_LEFT, CellStyle.BORDER_MEDIUM);
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.BORDER_RIGHT, CellStyle.BORDER_MEDIUM);
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.TOP_BORDER_COLOR,IndexedColors.RED.getIndex());
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.RED.getIndex());
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.LEFT_BORDER_COLOR,IndexedColors.RED.getIndex());
		CellUtil.setCellStyleProperty(cell, wb, CellUtil.RIGHT_BORDER_COLOR, IndexedColors.RED.getIndex());
		row.createCell(2).setCellValue(
				createHelper.createRichTextString("This is a string用户年龄"));
		row.createCell(3).setCellValue("用户手机");
		row.createCell(4).setCellValue("收货地址");
		row.createCell(5).setCellValue("注册时间");
		sheet.autoSizeColumn(0); // adjust width of the first column
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
