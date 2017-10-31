package com.decom.chunks.office;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/23.
 *
 * 用poi读取excel：包括 xls和xlsx两种格式
 *
 */
public class PoiReadExcel {
    /**
     * 几种打开excel的方式
     */
    public static void openExcelMethords(){
        Workbook workbook = null;
        File workbookFile = null;
        FileInputStream inputStream = null;

        //methord 1
        try {
            try {
                inputStream = new FileInputStream(workbookFile);
                workbook = WorkbookFactory.create(inputStream);

            } finally {
                if(inputStream != null)
                    inputStream.close();
            }

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        //methord 2, 读取2003格式excel的另一种方式
        try {
            try {
                inputStream = new FileInputStream(workbookFile);
                POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
                workbook = new HSSFWorkbook(poifsFileSystem);
            } finally {
                if(inputStream != null)
                    inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //methord 3,读取加密的excel
        try {
            try {
                inputStream = new FileInputStream(workbookFile);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                POIFSFileSystem poifsFileSystem = new POIFSFileSystem(bufferedInputStream);

                Biff8EncryptionKey.setCurrentUserPassword("excel password");

                workbook = new HSSFWorkbook(poifsFileSystem);

            } finally {
                if(inputStream != null)
                    inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取2003格式的excel文件
     * @param xlsFilePath
     */
    public static String readXlsFile(String xlsFilePath) {
        //用于存储读取的excel内容
        StringBuffer sb = new StringBuffer();
        try {
            //创建对工作薄的引用
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsFilePath));
            //工作薄中表格数量
            int sheetsCount = workbook.getNumberOfSheets();
            for (int sheetIndexInWorkbook = 0; sheetIndexInWorkbook < sheetsCount; sheetIndexInWorkbook++) {
                if (workbook.getSheetAt(sheetIndexInWorkbook) != null) {
                    HSSFSheet sheet = workbook.getSheetAt(sheetIndexInWorkbook);
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_BLANK:
                                    sb.append("--");
                                    break;
                                case HSSFCell.CELL_TYPE_BOOLEAN:
                                    sb.append(cell.getBooleanCellValue());
                                    break;
                                case HSSFCell.CELL_TYPE_ERROR:
                                    sb.append(cell.getErrorCellValue());
                                    break;
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    sb.append(cell.getCellFormula());
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    sb.append(cell.getNumericCellValue());
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    sb.append(cell.getStringCellValue());
                                    break;
                                default:
                                    break;
                            }
                            sb.append("\t");
                        }
                        sb.append("\r\n");//每一行换行
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 读取2007格式的excel
     * @param xlsxFilePath
     * @return
     */
    public static String readXlsxFilePoi(String xlsxFilePath){
        StringBuffer sb = new StringBuffer();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFilePath));
            int sheetCount = workbook.getNumberOfSheets();
            for(int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++){
                if(workbook.getSheetAt(sheetIndex) != null){
                    XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
                    int lastRowNum = sheet.getLastRowNum();
                    for(int rowIndex = 0; rowIndex < lastRowNum; rowIndex++){
                        if(sheet.getRow(rowIndex) != null){
                            XSSFRow row = sheet.getRow(rowIndex);
                            int lastCellNum = row.getLastCellNum();
                            for(int cellIndex = 0; cellIndex < lastCellNum; cellIndex++){

                                //Enum Row.MissingCellPolicy
                                if(row.getCell(cellIndex, Row.CREATE_NULL_AS_BLANK) != null){
                                    XSSFCell cell = row.getCell(cellIndex, Row.CREATE_NULL_AS_BLANK);
                                    switch (cell.getCellType()){
                                        case XSSFCell.CELL_TYPE_BLANK:
                                            sb.append("--");
                                            break;
                                        case XSSFCell.CELL_TYPE_BOOLEAN:
                                            sb.append(cell.getBooleanCellValue());
                                            break;
                                        case XSSFCell.CELL_TYPE_ERROR:
                                            sb.append(cell.getErrorCellValue());
                                            break;
                                        case XSSFCell.CELL_TYPE_FORMULA:
                                            sb.append(cell.getCellFormula());
                                            break;
                                        case XSSFCell.CELL_TYPE_NUMERIC:
                                            sb.append(cell.getNumericCellValue());
                                            break;
                                        case XSSFCell.CELL_TYPE_STRING:
                                            sb.append(cell.getStringCellValue());
                                            break;
                                        default:
                                            break;
                                    }
                                    sb.append("\t");
                                }
                            }
                        }
                        sb.append("\r\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String resourcePath = PoiReadExcel.class.getResource("/").getPath();
        String xlsFilePath = resourcePath + "poi/singleSheet.xls";

        String xlsString = PoiReadExcel.readXlsFile(xlsFilePath);
        System.out.println("读取2003格式excel内容:\r\n" + xlsString);

        String xlsxFilePath = resourcePath + "poi/singleSheet.xlsx";;
        String xlsxString = PoiReadExcel.readXlsxFilePoi(xlsxFilePath);
        System.out.println("读取2007格式excel内容：\r\n" + xlsxString);

    }


}
