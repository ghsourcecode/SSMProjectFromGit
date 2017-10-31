package com.decom.chunks.office;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 *
 * poi替换word 2007模板内容
 *
 */
public class PoiReplaceWordTemplate {
    /**
     * 替换word中的内容
     */
    public static void replaceWordTemplate(){
        String templateWordPath = PoiReplaceWordTemplate.class.getResource("/").getPath() + "poi/templateReplace.docx";
        try {
            FileInputStream fis = new FileInputStream(templateWordPath);
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                String replaced = paragraph.getText().replace("${reportDate}", LocalDate.now().toString());
                List<XWPFRun> runs = paragraph.getRuns();
                for (int j = 0; j < runs.size(); j++) {
                    paragraph.removeRun(j);
                }
                XWPFRun run = paragraph.createRun();
                run.setText(replaced);
            }

            String word2007Path = PoiReplaceWordTemplate.class.getResource("/").getPath() + "poi/templateReplaced.docx";
            FileOutputStream outputStream = new FileOutputStream(word2007Path);
            document.write(outputStream);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        PoiReplaceWordTemplate.replaceWordTemplate();
    }

}
