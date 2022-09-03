package com.procesverbal.procesverbal.helper;

import com.procesverbal.procesverbal.services.DocumentService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import static com.procesverbal.procesverbal.AppString.BORDER_SIZE;

@Component
public class Functions {
    static Logger logger = LoggerFactory.getLogger(DocumentService.class);
    public static int getImageFormat(String imgFileName) {
        int format;
        if (imgFileName.endsWith(".emf"))
            format = XWPFDocument.PICTURE_TYPE_EMF;
        else if (imgFileName.endsWith(".wmf"))
            format = XWPFDocument.PICTURE_TYPE_WMF;
        else if (imgFileName.endsWith(".pict"))
            format = XWPFDocument.PICTURE_TYPE_PICT;
        else if (imgFileName.endsWith(".jpeg") || imgFileName.endsWith(".jpg"))
            format = XWPFDocument.PICTURE_TYPE_JPEG;
        else if (imgFileName.endsWith(".png"))
            format = XWPFDocument.PICTURE_TYPE_PNG;
        else if (imgFileName.endsWith(".dib"))
            format = XWPFDocument.PICTURE_TYPE_DIB;
        else if (imgFileName.endsWith(".gif"))
            format = XWPFDocument.PICTURE_TYPE_GIF;
        else if (imgFileName.endsWith(".tiff"))
            format = XWPFDocument.PICTURE_TYPE_TIFF;
        else if (imgFileName.endsWith(".eps"))
            format = XWPFDocument.PICTURE_TYPE_EPS;
        else if (imgFileName.endsWith(".bmp"))
            format = XWPFDocument.PICTURE_TYPE_BMP;
        else if (imgFileName.endsWith(".wpg"))
            format = XWPFDocument.PICTURE_TYPE_WPG;
        else {
            return 0;
        }
        return format;
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public static String toFrenchNumber(int number) {
        switch (number) {
            case 1:
                return "premierère";
            case 2:
                return "deuxième";
            case 3:
                return "troisième";
            case 4:
                return "quatrième";
            case 5:
                return "cinquième";
            case 6:
                return "sixième";
            case 7:
                return "septième";
            case 8:
                return "huitième";
            case 9:
                return "neuvième";
            case 10:
                return "dixième";

            default:
                return "premierère";
        }
    }
    public static String readTextFile(String textPath) throws FileNotFoundException {
        try {
            File myObj = new File(textPath);
            String data = "";
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
            return data;
        } catch (FileNotFoundException e) {

            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new FileNotFoundException("Error with file " + textPath);
        }
    }
    public static XWPFDocument addNewLine(XWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.addBreak();
        return doc;

    }
    public static void exportDocument(XWPFDocument doc, String title) throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(title + ".docx");
            doc.write(out);
            out.close();
            doc.close();
        } catch (IOException e) {
            logger.error(e.getMessage());

            throw new IOException("error with document " + title);
        }

    }
    public static void setTableBorderColor(XWPFTable table, String color) {

        table.getCTTbl().getTblPr().getTblBorders().getBottom().setColor(color);
        table.getCTTbl().getTblPr().getTblBorders().getTop().setColor(color);
        table.getCTTbl().getTblPr().getTblBorders().getLeft().setColor(color);
        table.getCTTbl().getTblPr().getTblBorders().getRight().setColor(color);
        table.getCTTbl().getTblPr().getTblBorders().getInsideH().setColor(color);
        table.getCTTbl().getTblPr().getTblBorders().getInsideV().setColor(color);

        table.getCTTbl().getTblPr().getTblBorders().getRight().setSz(BigInteger.valueOf(BORDER_SIZE));
        table.getCTTbl().getTblPr().getTblBorders().getTop().setSz(BigInteger.valueOf(BORDER_SIZE));
        table.getCTTbl().getTblPr().getTblBorders().getLeft().setSz(BigInteger.valueOf(BORDER_SIZE));
        table.getCTTbl().getTblPr().getTblBorders().getBottom().setSz(BigInteger.valueOf(BORDER_SIZE));
        table.getCTTbl().getTblPr().getTblBorders().getInsideH().setSz(BigInteger.valueOf(BORDER_SIZE));
        table.getCTTbl().getTblPr().getTblBorders().getInsideV().setSz(BigInteger.valueOf(BORDER_SIZE));
    }



}
