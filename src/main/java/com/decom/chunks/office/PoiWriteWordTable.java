package com.decom.chunks.office;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 *
 * 2003格式：
 * Range：它表示一个范围，这个范围可以是整个文档，也可以是里面的某一小节（Section），也可以是某一个段落（Paragraph），
 * 还可以是拥有共同属性的一段文本（CharacterRun）。
 * Section：word文档的一个小节，一个word文档可以由多个小节构成。
 * Paragraph：word文档的一个段落，一个小节可以由多个段落构成。
 * CharacterRun：具有相同属性的一段文本，一个段落可以由多个CharacterRun组成。
 * Table：一个表格。
 * TableRow：表格对应的行。
 * TableCell：表格对应的单元格。
 *
 * 2007格式：
 * XWPFParagraph：代表一个段落。
 * XWPFRun：代表具有相同属性的一段文本。
 * XWPFTable：代表一个表格。
 * XWPFTableRow：表格的一行。
 * XWPFTableCell：表格对应的一个单元格。
 */
public class PoiWriteWordTable {
    public static void createWord2007(){
        XWPFDocument document = new XWPFDocument(); //代表一个文档

        //标题
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = titleParagraph.createRun();
        run.setBold(true);
        run.setFontFamily("微软雅黑");
        run.setColor("000000");
        run.setFontSize(20);
        run.setText("种类报告");

        XWPFTable table = createSimpleTable(document);

        String wordPath = PoiWriteWordTable.class.getResource("/").getPath() + "writeTableToWord2007.docx";
        try {
            FileOutputStream outputStream = new FileOutputStream(wordPath);
            document.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static XWPFTable createSimpleTable(XWPFDocument document){
        XWPFTable table = document.createTable();                           //创建完表格后，默认带有一行空记录
        table.removeRow(0);                                                 //清除新建表格时带有的空行
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr ctTblPr = ctTbl.getTblPr() != null ? ctTbl.getTblPr() : ctTbl.addNewTblPr();
        CTTblWidth ctTblWidth = ctTblPr.isSetTblW() ? ctTblPr.getTblW() : ctTblPr.addNewTblW();
        ctTblWidth.setW(BigInteger.valueOf(8000));
        ctTblWidth.setType(STTblWidth.DXA);

        XWPFTableRow row = table.createRow();
        row.setHeight(100);
        XWPFTableCell cell = row.addNewTableCell();
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        setXwpfTableCellValue(cell, "种类");
        cell = row.addNewTableCell();

        mergeCellHorizontally(table, 0, 0, 1);   // 合并第一行前两列
        cell = row.addNewTableCell();
        setXwpfTableCellValue(cell, "总分");
        cell = row.addNewTableCell();
        setXwpfTableCellValue(cell, "实得");
        cell = row.addNewTableCell();
        setXwpfTableCellValue(cell, "备注");

        for(int i = 0; i < 7; i++){
            row = table.createRow();
            cell = row.getCell(0);
            setXwpfTableCellValue(cell, "种类" + i);
            cell = row.getCell(1);
            setXwpfTableCellValue(cell, "种类细则" + i);
            cell = row.getCell(2);
            setXwpfTableCellValue(cell, "" + i);
            cell = row.getCell(3);
            setXwpfTableCellValue(cell, "" + i);
            cell = row.getCell(4);
            setXwpfTableCellValue(cell, "测试文字");
        }

        row = table.createRow();

        mergeCellHorizontally(table, 8, 0, 3);
        mergeCellHorizontally(table, 8, 3, 4);
        setXwpfTableCellValue(table, 8, 0, "总结");
        setXwpfTableCellValue(table, 8, 3, "50");

        row = table.createRow();

        mergeCellHorizontally(table, 9, 1, 4);
        setXwpfTableCellValue(table, 9, 0, "合计");
        setXwpfTableCellValue(table, 9, 1, "总结");
        setCellParagraphAlignment(table, 9, 1, ParagraphAlignment.CENTER);

        row = table.createRow();
        row = table.createRow();
//        mergeCellHorizontally(table, 10, 0, 2);
        mergeCellHorizontally(table, 11, 0, 2);
//        mergeCellsVertically(table, 4, 11, 12);
        setXwpfTableCellValue(table, 10, 0, "列合并测试");

        row = table.createRow();
        mergeCellsVertically(table, 3, 10, 12);

        return table;
    }

    private static void setXwpfTableCellValue(XWPFTableCell cell, String cellValue){
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, cell);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setText(cellValue);
        run.setColor("000000");
        CTRPr ctrPr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTFonts fonts = ctrPr.isSetRFonts() ? ctrPr.getRFonts() : ctrPr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        cell.setParagraph(paragraph);
    }

    /**
     * 设置单元格字体和内容
     * @param table
     * @param rowIndex
     * @param colIndex
     * @param cellValue
     */
    private static void setXwpfTableCellValue(XWPFTable table, int rowIndex, int colIndex, String cellValue){
        XWPFTableCell cell = table.getRow(rowIndex).getCell(colIndex);
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, cell);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setColor("000000");
        run.setText(cellValue);
        CTRPr ctrPr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTFonts fonts = ctrPr.isSetRFonts() ? ctrPr.getRFonts() : ctrPr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        cell.setParagraph(paragraph);
    }

    /**
     * 设置单元格文本对齐方式
     * @param table
     * @param row
     * @param col
     */
    private static void setCellParagraphAlignment(XWPFTable table, int row, int col, ParagraphAlignment alignment){
        XWPFTableCell cell = table.getRow(row).getCell(col);
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph paragraph = paragraphs.get(i);
            paragraph.setAlignment(alignment);
        }
    }

    /**
     * 合并列
     * @param table         表格
     * @param row           要合并的行索引     索引从0开始
     * @param fromCol       开始列             索引从0开始
     * @param toCol         结束列             索引从0开始
     */
    static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
        for(int colIndex = fromCol; colIndex <= toCol; colIndex++){
            CTHMerge hmerge = CTHMerge.Factory.newInstance();
            if(colIndex == fromCol){
                // The first merged cell is set with RESTART merge value
                hmerge.setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                hmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(row).getCell(colIndex);
            // Try getting the TcPr. Not simply setting an new one every time.
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setHMerge(hmerge);
            } else {
                // only set an new TcPr if there is not one already
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setHMerge(hmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }

    /**
     * 合并行
     * @param table     表格
     * @param col       要合并行的列索引       索引从0开始
     * @param fromRow   开始合并行索引         索引从0开始
     * @param toRow     结束合并行索引         索引从0开始
     */
    private static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow){
        for(int row = fromRow; row <= toRow; row++){
            CTVMerge ctvMerge = CTVMerge.Factory.newInstance();
            if(row == fromRow){
                // The first merged cell is set with RESTART merge value
                ctvMerge.setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                ctvMerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(fromRow).getCell(col);
            // Try getting the TcPr. Not simply setting an new one every time.
            CTTcPr ctTcPr = cell.getCTTc().getTcPr();
            if(ctTcPr != null){
                ctTcPr.setVMerge(ctvMerge);
            } else {
                // only set an new TcPr if there is not one already
                ctTcPr = CTTcPr.Factory.newInstance();
                ctTcPr.setVMerge(ctvMerge);
                cell.getCTTc().setTcPr(ctTcPr);
            }
        }
    }

    public static void main(String[] args) {

        PoiWriteWordTable.createWord2007();

    }
}
