package cn.netbuffer.jfinal_bootstrap_table.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class POIExcelUtil {

    public static List<List<String>> read(String path) {
        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(path)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        if (workbook == null) {
            return null;
        }
//        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
//        }
        Sheet sheet = workbook.getSheetAt(0);
        int firstRowNum = sheet.getFirstRowNum();
        Row firstRow = sheet.getRow(firstRowNum);
        if (null == firstRow) {
            log.warn("解析Excel失败，在第一行没有读取到任何数据！");
        }
        // 解析每一行的数据，构造数据对象
        int rowStart = firstRowNum + 1;
        int rowEnd = sheet.getPhysicalNumberOfRows();
        List<List<String>> data = new ArrayList<>();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (null == row) {
                continue;
            }
            int cells = row.getPhysicalNumberOfCells();
            List<String> arr = new ArrayList<>(cells);
            for (int i = 0; i < cells; i++) {
                CellType cellType = row.getCell(i).getCellType();
                if (cellType == CellType.NUMERIC) {
                    arr.add(String.valueOf(row.getCell(i).getNumericCellValue()));
                } else if (cellType == CellType.STRING) {
                    arr.add(row.getCell(i).getStringCellValue());
                }
            }
            data.add(arr);
        }
        return data;
    }

    public static void write(List<String> titles, List<Map<String, Object>> datas, String path) {
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
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(titleFont);
        for (int i = 0; i < titleCount; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(cellStyle);
        }
        CellStyle contentStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
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
