package cn.netbuffer.jfinal_bootstrap_table.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class POIExcelUtil {

    public static void export(List<String> titles, List<Map<String, Object>> datas, String path) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(titles.get(0));
        // 冻结该行，使其无法移动
        sheet.createFreezePane(0, 1, 0, 1);
//        Header header = sheet.getHeader();
//        header.setCenter("Center Header");
//        header.setLeft("Left Header");
//        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic");
//        HSSFHeader.fontSize((short) 16);
        // "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints(30);
        int titleCount = titles.size();
        Font titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.AQUA.getIndex());
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setFont(titleFont);
        for (int i = 0; i < titleCount; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(cellStyle);
        }
        CellStyle contentStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        int dataCount = datas.size();
        for (int i = 1; i < dataCount + 1; i++) {
            Row rowIndex = sheet.createRow((short) i);
            Map<String, Object> data = datas.get(i - 1);
            Set<String> dataSet = data.keySet();
            Iterator<String> d = dataSet.iterator();
            int k = 0;
            while (d.hasNext()) {
                Cell cell = rowIndex.createCell(k);
                cell.setCellStyle(contentStyle);
                String key = d.next();
                cell.setCellValue(data.get(key) == null ? "" : data.get(key).toString());
                k++;
            }
        }
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
