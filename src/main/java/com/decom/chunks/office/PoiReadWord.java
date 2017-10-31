package com.decom.chunks.office;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 *
 * 采用poi读取word
 *
 */
public class PoiReadWord {

    public static void readWord2007(){
        String word2007Path = PoiReadWord.class.getResource("/").getPath() + "poi/word2007Example.docx";
        try {
            FileInputStream inputStream = new FileInputStream(word2007Path);

            int curTable = 0;//当前操作对象的索引
            int curParagraph = 0;//当前操作对象的索引

            XWPFDocument document = new XWPFDocument(inputStream);

            Iterator iBody = document.getBodyElementsIterator();
            while (iBody.hasNext()) {
                IBodyElement bodyElement = (IBodyElement) iBody.next();
                BodyElementType bodyType = bodyElement.getElementType();

                if(bodyType.equals(BodyElementType.TABLE)) {
                    XWPFTable xwpfTable = bodyElement.getBody().getTableArray(curTable);
                    if(xwpfTable != null) {
                        readWrod2007Table(xwpfTable);
                        curTable++;
                    }
                } else if(bodyType.equals(BodyElementType.CONTENTCONTROL)) {

                } else if(bodyType.equals(BodyElementType.PARAGRAPH)) {
                    XWPFParagraph paragraph = bodyElement.getBody().getParagraphArray(curParagraph);
                    if(paragraph != null) {
                        String content = paragraph.getText();
                        curParagraph++;
                        System.out.println(content);
                    }
                } else {
                    System.out.println("不识别的 wrod 2007 段落类型！");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 提取文档中的文字内容
     */
    public static void extractorWord2007Paragraph(){
        String word2007Path = PoiReadWord.class.getResource("/").getPath() + "poi/word2007Example.docx";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(word2007Path);
            XWPFDocument document = new XWPFDocument(inputStream);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                readWrod2007Paragraph(paragraph);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析word 2007段落
     * @param paragraph
     */
    private static void readWrod2007Paragraph(XWPFParagraph paragraph){

    }

    /**
     * 提取word 2007中的图片，存储
     */
    public static void extractorWord2007Pictures(){
        String word2007Path = PoiReadWord.class.getResource("/").getPath() + "poi/word2007Example.docx";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(word2007Path);
            XWPFDocument document = new XWPFDocument(inputStream);

            List<XWPFPictureData> pictures = document.getAllPictures();
            for (int i = 0; i < pictures.size(); i++) {
                XWPFPictureData xwpfPictureData = pictures.get(i);

                System.out.println(xwpfPictureData.getFileName());
                System.out.println(xwpfPictureData.getPictureType());

                String picPath = PoiReadWord.class.getResource("/").getPath() + "xwpfPictureData.getFileName()";
                FileOutputStream pictureOutput = new FileOutputStream(picPath);
                byte[] picData = xwpfPictureData.getData();
                pictureOutput.write(picData);
                pictureOutput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取字word 2007中的表格
     */
    public static void extractorWord2007Tables(){
        String word2007Path = PoiReadWord.class.getResource("/").getPath() + "poi/word2007Example.docx";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(word2007Path);
            XWPFDocument document = new XWPFDocument(inputStream);

            Iterator iterator = document.getTablesIterator();

            while (iterator.hasNext()) {
                XWPFTable table = (XWPFTable) iterator.next();
                readWrod2007Table(table);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取word 2007中的table
     * @param xwpfTable
     */
    private static void readWrod2007Table(XWPFTable xwpfTable){
        List<XWPFTableRow> rows = xwpfTable.getRows();
        for(XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for(XWPFTableCell cell : cells){
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                for (int i = 0; i < paragraphs.size(); i++) {
                    XWPFParagraph paragraph = paragraphs.get(i);
                    String content = paragraph.getText();
                    System.out.println(content);

                    List<XWPFRun> runs = paragraph.getRuns();
                    for (int j = 0; j < runs.size(); j++) {
                        XWPFRun run = runs.get(j);
                        String runText = run.getText(run.getTextPosition());
                        if(runText == null) {                         //runtext为空，可能为附件或图片，此处公处理图片
                            List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
                            for (int k = 0; k < embeddedPictures.size(); k++) {
                                XWPFPicture xwpfPicture = embeddedPictures.get(k);
                                XWPFPictureData pictureData = xwpfPicture.getPictureData();
                                System.out.println(pictureData.getFileName());
                            }
                        } else {
                            System.out.println(runText);
                        }

                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        PoiReadWord.readWord2007();
    }

}

