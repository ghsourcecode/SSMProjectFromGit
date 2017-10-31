package com.decom.chunks.office;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/10/24.
 */
public class PoiXssfWriteExcel {
    /**
     * 写普通字符串到2007格式excel
     */
    public static void writeStringToExcel() {
        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //create blank sheet
        XSSFSheet sheet = workbook.createSheet("sheetname");
        XSSFRow row = null;
        //this data needs to be written(object[])
        Map<String, Object[]> employeeInfo = new TreeMap<>();
        employeeInfo.put("1", new Object[]{"EMP ID", "EMP NAME", "DESCRIPTION"});
        employeeInfo.put("2", new Object[]{"tp01", "Gopal", "Technical Manager"});
        employeeInfo.put("3", new Object[]{"tp02", "Manisha", "Proof Reader"});
        employeeInfo.put("4", new Object[]{"tp03", "Masthan", "Technical Writer"});
        employeeInfo.put("5", new Object[]{"tp04", "Satish", "Technical Writer"});
        employeeInfo.put("6", new Object[]{"tp05", "Krishna", "Technical Writer"});
        //iterator over data and write to sheet
        int rowid = 0;
        Set<String> keyIdSet = employeeInfo.keySet();
        for (String key : keyIdSet) {
            row = sheet.createRow(rowid++);
            Object[] rowData = employeeInfo.get(key);
            int cellid = 0;
            for (Object obj : rowData) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //write the workbook in file system
        FileOutputStream outputStream = null;
        try {
            try {
                String workbookFilePath = PoiXssfWriteExcel.class.getResource("/").getPath() + "workbook.xlsx";
                File workbookFile = new File(workbookFilePath);
                if (workbookFile.exists())
                    workbookFile.delete();

                outputStream = new FileOutputStream(workbookFile);
                workbook.write(outputStream);
            } finally {
                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定单元格格式
     */
    public static void createDifferentTypeCell(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheetname");
        int rowId = 2;                                      //rowId为行索引，从0开始, 索引2即为第3行
        XSSFRow row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("type of cell");
        row.createCell(1).setCellValue("cell value");
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: blank");
        row.createCell(1);
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: boolean");
        row.createCell(1).setCellValue(true);
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: error");
        row.createCell(1).setCellValue(XSSFCell.CELL_TYPE_ERROR);
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: date");
        row.createCell(1).setCellValue(new Date());
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: numeric");
        row.createCell(1).setCellValue(20);
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: string");
        row.createCell(1).setCellValue("stringvalue");
        row = sheet.createRow(rowId++);
        row.createCell(0).setCellValue("cell type: formula");
        row.createCell(1).setCellValue(102);
        row.createCell(2).setCellValue(203);
        row.createCell(3).setCellFormula("SUM(B" + rowId + ":C" + rowId + ")");

        String workbookPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "celltype.xlsx";
        File workbookFile = new File(workbookPath);
        if(workbookFile.exists())
            workbookFile.delete();

        try {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(workbookFile);
                workbook.write(outputStream);

            } finally {
                if(outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置单元格式样式
     */
    public static void createCellStyle() {
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheetname");
        XSSFRow row = sheet.createRow(1);
        row.setHeight((short) 800);
        Cell cell = row.createCell(1);
        cell.setCellValue("test of merging");

        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(
                1, //first row
                1, //last row
                1, //first column
                4));//last column

        //单元格内容对齐
        row = sheet.createRow(3);
        row.setHeight((short) 800);
        cell = row.createCell(0);
        XSSFCellStyle style1 = workbook.createCellStyle();//左对齐
        style1.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
        cell.setCellValue("top left");
        cell.setCellStyle(style1);

        row = sheet.createRow(4);
        row.setHeight((short) 800);
        cell = row.createCell(1);
        XSSFCellStyle style2 = workbook.createCellStyle();//居中
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cell.setCellValue("center align");
        cell.setCellStyle(style2);

        row = sheet.createRow(5);
        row.setHeight((short) 800);
        cell = row.createCell(2);
        XSSFCellStyle style3 = workbook.createCellStyle();//右下角
        style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style3.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        cell.setCellValue("bottom right");
        cell.setCellStyle(style3);

        row = sheet.createRow(6);
        row.setHeight((short) 800);
        cell = row.createCell(3);
        XSSFCellStyle style4 = workbook.createCellStyle();//分散对齐
        style4.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);
        style4.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
        cell.setCellValue("Contents are Justified in Alignment");
        cell.setCellStyle(style4);

        //边框样式
        row = sheet.createRow(8);
        row.setHeight((short) 800);
        cell = row.createCell(1);
        XSSFCellStyle style5 = workbook.createCellStyle();
        style5.setBorderBottom(XSSFCellStyle.BORDER_THICK);             //下边框
        style5.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        style5.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE);              //左边框
        style5.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        style5.setBorderTop(XSSFCellStyle.BIG_SPOTS);                   //上边框
        style5.setTopBorderColor(IndexedColors.CORAL.getIndex());
        style5.setBorderRight(XSSFCellStyle.BORDER_HAIR);               //右边框
        style5.setRightBorderColor(IndexedColors.RED.getIndex());
        cell.setCellValue("borderstyle");
        cell.setCellStyle(style5);

        //填充背景色
        row = sheet.createRow(10);
        cell = row.createCell(1);
        XSSFCellStyle style6 = workbook.createCellStyle();
        style6.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
        style6.setAlignment(XSSFCellStyle.ALIGN_FILL);
        sheet.setColumnWidth(1, 8000);
        cell.setCellValue("fill background/pattern");
        cell.setCellStyle(style6);

        //填充前景色
        row = sheet.createRow(12);
        cell = row.createCell(1);
        XSSFCellStyle style7 = workbook.createCellStyle();
        style7.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style7.setFillPattern(XSSFCellStyle.LESS_DOTS);
        style7.setAlignment(XSSFCellStyle.ALIGN_FILL);
        cell.setCellValue("fill foreground/pattern");
        cell.setCellStyle(style7);

        String cellStyleExcelPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "cellstyle.xlsx";
        File cellStyleFile = new File(cellStyleExcelPath);
        if(cellStyleFile.exists())
            cellStyleFile.delete();
        try {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(cellStyleFile);
                workbook.write(outputStream);

            } finally {
                if(outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建字体样式
     */
    public static void createFontStyle(){
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("fontstyle");
        XSSFRow row = sheet.createRow(1);
        row.setHeight((short) 800);
        Cell cell = row.createCell(1);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 30);
        font.setFontName("IMPACT");
        font.setItalic(true);
        font.setColor(IndexedColors.GREEN.getIndex());

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        cell.setCellValue("font style");
        cell.setCellStyle(style);

        row = sheet.createRow(5);
        cell = row.createCell(1);
        XSSFCellStyle styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 0);
        cell.setCellValue("0 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(3);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 30);
        cell.setCellValue("30 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(5);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 90);
        cell.setCellValue("90 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(7);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 120);
        cell.setCellValue("120 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(9);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 180);
        cell.setCellValue("180 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(11);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 270);
        cell.setCellValue("270 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(13);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 300);
        cell.setCellValue("300 degree");
        cell.setCellStyle(styleDegree);

        cell = row.createCell(15);
        styleDegree = workbook.createCellStyle();
        styleDegree.setRotation((short) 360);
        cell.setCellValue("360 degree");
        cell.setCellStyle(styleDegree);

        String fontStylePath = PoiXssfWriteExcel.class.getResource("/").getPath() + "fontstyle.xlsx";
        File fontStyleFile = new File(fontStylePath);
        if(fontStyleFile.exists())
            fontStyleFile.delete();
        try {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fontStyleFile);
                workbook.write(outputStream);

            } finally {
                if(outputStream != null)
                    outputStream.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建包含公式的2007格式excel
     */
    public static void createFomula(){
        //创建空excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("formulaname");

        XSSFRow row = sheet.createRow(1);
        Cell cell = row.createCell(1);
        cell.setCellValue("A = ");
        cell = row.createCell(2);
        cell.setCellValue(5);           //第1个数值

        row = sheet.createRow(2);
        cell = row.createCell(1);
        cell.setCellValue("B = ");
        cell = row.createCell(2);
        cell.setCellValue(4);          //第2个数值

        row = sheet.createRow(3);
        cell = row.createCell(1);
        cell.setCellValue("total = ");
        cell = row.createCell(2);
        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula("sum(c2:c3)");
        Cell copy = row.createCell(3);
        copy.setCellValue(cell.getCellFormula());

        row = sheet.createRow(4);
        cell = row.createCell(1);
        cell.setCellValue("power =");
        cell = row.createCell(2);
        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula("power(c2,c3)");
        copy = row.createCell(3);
        copy.setCellValue(cell.getCellFormula());

        row = sheet.createRow(5);
        cell = row.createCell(1);
        cell.setCellValue("max = ");
        cell = row.createCell(2);
        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula("max(c2:c3)");
        copy = row.createCell(3);
        copy.setCellValue(cell.getCellFormula());

        row = sheet.createRow(6);
        cell = row.createCell(1);
        cell.setCellValue("fact = ");
        cell = row.createCell(2);
        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula("fact(c3)");
        copy = row.createCell(3);
        copy.setCellValue(cell.getCellFormula());

        row = sheet.createRow(7);
        cell = row.createCell(1);
        cell.setCellValue("sqrt = ");
        cell = row.createCell(2);
        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula("sqrt(c3)");
        copy = row.createCell(3);
        copy.setCellValue(cell.getCellFormula());

        String formulaPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "formulaType.xlsx";
        File formulaFile = new File(formulaPath);
        if(formulaFile.exists())
            formulaFile.delete();
        try {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(formulaFile);
                workbook.write(outputStream);
            } finally {
                if(outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建超链接
     */
    public static void createHyperlink(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("hyperlinkname");
        XSSFRow row = sheet.createRow(1);

        XSSFFont hyperlinkFont = workbook.createFont();
        hyperlinkFont.setUnderline(XSSFFont.U_SINGLE);
        hyperlinkFont.setColor(IndexedColors.BLUE.getIndex());
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(hyperlinkFont);

        Cell cell = row.createCell(1);
        cell.setCellValue("url link");

        CreationHelper creationHelper = workbook.getCreationHelper();
        XSSFHyperlink link = (XSSFHyperlink) creationHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress("https://www.baidu.com");
        cell.setHyperlink(link);
        cell.setCellStyle(style);

        row = sheet.createRow(2);
        cell = row.createCell(1);
        cell.setCellValue("file link");
        link = (XSSFHyperlink) creationHelper.createHyperlink(Hyperlink.LINK_FILE);
        link.setAddress("formulaType.xlsx");
        cell.setHyperlink(link);
        cell.setCellStyle(style);

        row = sheet.createRow(3);
        cell = row.createCell(1);
        cell.setCellValue("email link");
        link = (XSSFHyperlink) creationHelper.createHyperlink(Hyperlink.LINK_EMAIL);
        link.setAddress("123456789@qq.com");
        cell.setHyperlink(link);
        cell.setCellStyle(style);

        String hyperlinkPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "hyperlink.xlsx";
        File hyperlinkFile = new File(hyperlinkPath);
        if(hyperlinkFile.exists())
            hyperlinkFile.delete();

        try {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(hyperlinkFile);
                workbook.write(outputStream);
            } finally {
                if(outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印示例
     */
    public static void printSetting(){
        XSSFWorkbook workbook = null;
        String excelPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "formulaType.xlsx";
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelPath));
//            workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);
            workbook = new XSSFWorkbook(inputStream);

            int sheets = workbook.getNumberOfSheets();
            if(sheets >= 1){
                XSSFSheet sheet = workbook.getSheetAt(0);
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                XSSFRow firstRow = sheet.getRow(firstRowNum);
                int firstColNum = firstRow.getFirstCellNum();
                int lastColNum = firstRow.getLastCellNum();

                workbook.setPrintArea(
                        0,              //工作薄中的表索引，sheet index
                        firstColNum,    //开始列，start column
                        lastColNum,     //结束列，end column
                        firstRowNum,    //开始行，start row
                        lastRowNum);    //结束行，end row

                XSSFPrintSetup printSetup = sheet.getPrintSetup();
                printSetup.setPaperSize(XSSFPrintSetup.A3_PAPERSIZE);//设置纸张大小
                printSetup.setLandscape(true);
                sheet.setDisplayGridlines(true);
                sheet.setPrintGridlines(true);

                FileOutputStream outputStream = new FileOutputStream(excelPath);
                workbook.write(outputStream);
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取mysql 数据库，创建2007 格式excel
     */
    public static void createExcelByReadMySql() {
        try {
            FileOutputStream outputStream = null;
            Connection connection = null;
            Statement statement = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mysql2017",//url
                        "root",//username
                        "root"//password
                );

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from emp_table");

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("employe");//创建表，并指定表名
                XSSFRow row = sheet.createRow(1);
                XSSFCell cell = null;
                cell = row.createCell(1);
                cell.setCellValue("emp id");
                cell = row.createCell(2);
                cell.setCellValue("emp name");
                cell = row.createCell(3);
                cell.setCellValue("deg");
                cell = row.createCell(4);
                cell.setCellValue("salary");
                cell = row.createCell(5);
                cell.setCellValue("dept");
                int i = 2;
                while (resultSet.next()){
                    row = sheet.createRow(i);
                    cell = row.createCell(1);
                    cell.setCellValue(resultSet.getInt(1));
                    cell = row.createCell(2);
                    cell.setCellValue(resultSet.getString(2));
                    cell = row.createCell(3);
                    cell.setCellValue(resultSet.getString(3));
                    cell = row.createCell(4);
                    cell.setCellValue(resultSet.getString(4));
                    cell = row.createCell(5);
                    cell.setCellValue(resultSet.getString(5));
                    i++;
                }

                String excelPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "contentFromMysql.xlsx";
                outputStream = new FileOutputStream(excelPath);
                workbook.write(outputStream);

            } finally {
                if(outputStream != null)
                    outputStream.close();
                if(statement != null)
                    statement.close();
                if(connection != null)
                    connection.close();
            }
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将图片插入excel
     */
    public static void addPictureToExcel(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("picture");
        String imgPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "poi/blue.jpg";
        BufferedImage bufferedImage = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            bufferedImage = ImageIO.read(new File(imgPath));
            ImageIO.write(
                    bufferedImage,
                    "jpg",              //此处图片类型不能带 “.”
                    byteArrayOutputStream
            );

            XSSFClientAnchor anchor = new XSSFClientAnchor(
                    400 * 256,  //dx1, 指图片在col1、row1指定单元格内 x 方向偏移, 单位是 1/256字符
                    300 * 256,  //dy1,
                    500 * 256, //dx2,指图片在col2、row3指定单元格内x方向偏移
                    400 * 256, //dy2,
                    1,  //col1,指图片填充的起始单元格行列号
                    1,  //row1,
                    8,  //col2,
                    15   //row2,
            );
            anchor.setAnchorType(XSSFClientAnchor.MOVE_AND_RESIZE);

            XSSFDrawing xssfDrawing = sheet.createDrawingPatriarch();   //画图管理器，每张表只能有一个
            xssfDrawing.createPicture(anchor, workbook.addPicture(byteArrayOutputStream.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();

            String excelPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "picture.xlsx";
            FileOutputStream outputStream = new FileOutputStream(excelPath);
            workbook.write(outputStream);

            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建页眉和页脚
     */
    public static void createHeaderAndFooter(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("headerAndFooter");
        XSSFHeaderFooter header = (XSSFHeaderFooter) sheet.getHeader();
        header.setLeft("left header");

        XSSFHeaderFooter footer = (XSSFHeaderFooter) sheet.getFooter();
        footer.setRight("right footer");

        XSSFRow row = sheet.createRow(1);
        row.createCell(1).setCellValue("header footer example");

        String headerPath = PoiXssfWriteExcel.class.getResource("/").getPath() + "headerfooter.xlsx";
        try {
            FileOutputStream outputStream = new FileOutputStream(headerPath);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建下载框 2007 和2003 不一样
     */
    public static void createDropdownList(){
        //2003格式
        String[] citys = {"北京", "天津", "上海", "深圳", "重庆"};
        CellRangeAddressList rangeAddressList = new CellRangeAddressList(   //指定生效的单元格范围
                1,  // first row
                3,  // last row
                1,  // first col
                10  // last col
        );

        DVConstraint constraint = DVConstraint.createExplicitListConstraint(citys); //指定下拉框可选范围

        HSSFDataValidation dataValidation2003 = new HSSFDataValidation(rangeAddressList, constraint);//绑定单无格区域和约束

        HSSFWorkbook workbook2003 = new HSSFWorkbook();
        HSSFSheet sheet2003 = workbook2003.createSheet("2003格式dropdown");
        sheet2003.addValidationData(dataValidation2003);

        String excel2003Path = PoiXssfWriteExcel.class.getResource("/").getPath() + "dropdown2003.xls";
        try {
            FileOutputStream outputStream2003 = new FileOutputStream(excel2003Path);
            workbook2003.write(outputStream2003);
            outputStream2003.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2007格式excel
        XSSFWorkbook workbook2007 = new XSSFWorkbook();
        XSSFSheet sheet2007 = workbook2007.createSheet("2007格式dropdown");
        XSSFDataValidationHelper dataValidationHelper = new XSSFDataValidationHelper(sheet2007);
        DataValidationConstraint dataValidationConstraint = dataValidationHelper.createExplicitListConstraint(citys);
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(
                -1,  // first row
                -1, // last row
                -1,  // first col
                -1   // last col
        );
        DataValidation dataValidation2007 = dataValidationHelper.createValidation(dataValidationConstraint, cellRangeAddressList);
        sheet2007.addValidationData(dataValidation2007);
        String excel2007Path = PoiXssfWriteExcel.class.getResource("/").getPath() + "dropdown2007.xls";
        try {
            FileOutputStream outputStream2007 = new FileOutputStream(excel2007Path);
            workbook2003.write(outputStream2007);
            outputStream2007.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

//        PoiXssfWriteExcel.writeStringToExcel();

//        PoiXssfWriteExcel.createDifferentTypeCell();

//        PoiXssfWriteExcel.createCellStyle();

//        PoiXssfWriteExcel.createFontStyle();

//        PoiXssfWriteExcel.createFomula();

//        PoiXssfWriteExcel.createHyperlink();

//        PoiXssfWriteExcel.printSetting();

//        PoiXssfWriteExcel.createExcelByReadMySql();

//        PoiXssfWriteExcel.addPictureToExcel();

        PoiXssfWriteExcel.createHeaderAndFooter();

//        PoiXssfWriteExcel.createDropdownList();

    }


}
