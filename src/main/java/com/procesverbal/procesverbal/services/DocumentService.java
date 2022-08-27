package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.AppString;
import com.procesverbal.procesverbal.dto.DocumentDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import static com.procesverbal.procesverbal.AppString.getImageFormat;

@Service
public class DocumentService {
    Logger logger = LoggerFactory.getLogger(DocumentService.class);

    public void createDocument(DocumentDto documentDto){
        try {
            XWPFDocument document=   new XWPFDocument();

            document=creatHeaderOfDocument(document,documentDto.getObjet(), documentDto.getAooNumber(), documentDto.getSeanceTitle());



            exportDocument( document, documentDto.getTitle());

        }catch (Exception e){
            logger.error(e.getMessage());

        }

    };
    public  XWPFDocument creatHeaderOfDocument(XWPFDocument doc ,String objet,String seanceNumber,int seanceTitle){
        try {
            //set logo image
            doc= addImagesToWordDocument(doc, AppString.LOGO);
            //set Aoo number
            XWPFParagraph seanceNumberParagraph = doc.createParagraph();
            seanceNumberParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun seanceNumberRun = seanceNumberParagraph.createRun();
            seanceNumberRun.setBold(true);
            seanceNumberRun.setFontSize(11);
            seanceNumberRun.setFontFamily("Univers 57 Condensed");
            seanceNumberRun.setText("AOO N° "+seanceNumber.toUpperCase());
            seanceNumberRun.setUnderline(UnderlinePatterns.SINGLE);
            //set under line image
            doc= addImagesToWordDocument(doc, AppString.UNDER_LINE);
            //break line
            doc=addNewLine(doc);
            //set object paragraph
            XWPFParagraph ObjetParagraph = doc.createParagraph();
            ObjetParagraph.setIndentationFirstLine(720);
            XWPFRun objetRun1 = ObjetParagraph.createRun();
            objetRun1.setFontSize(11);
            objetRun1.setUnderline(UnderlinePatterns.SINGLE);
            objetRun1.setFontFamily("Times New Roman");
            objetRun1.setText(readTextFile(AppString.OBJET_TEXT));
            XWPFRun objetRun2 = ObjetParagraph.createRun();
            objetRun2.setFontSize(12);
            objetRun2.setBold(true);
            objetRun2.setFontFamily("Times New Roman");
            objetRun2.setText(objet);
            doc=addNewLine(doc);
            doc=setSeanceTitle(doc,seanceTitle);
            return  doc;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public  XWPFDocument addImagesToWordDocument(XWPFDocument doc,String imagePath) throws IOException {
       try {
            XWPFParagraph p = doc.createParagraph();
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r = p.createRun();
            File imageFile = new File(imagePath);
            BufferedImage bimg1 = ImageIO.read(imageFile);
            int width1 = bimg1.getWidth();
            int height1 = bimg1.getHeight();
            String imgFile1 = imageFile.getName();
            int imgFormat1 = getImageFormat(imgFile1);
            r.addPicture(new FileInputStream(imageFile), imgFormat1, imgFile1, Units.toEMU(width1), Units.toEMU(height1));
            r.addBreak();
            return doc;
        }catch (IOException e){
           logger.error(e.getMessage());

           throw new IOException("error with image " + imagePath);

       }catch (InvalidFormatException e){
           logger.error(e.getMessage());
           throw new IOException("error with image " + imagePath);

       }
    }
    public  String readTextFile(String textPath) throws FileNotFoundException {
        try {
            File myObj = new File(textPath);
            String data ="";
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                 data = myReader.nextLine();
            }
            myReader.close();
            return  data;
        } catch (FileNotFoundException e) {

            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new FileNotFoundException("Error with file " + textPath);
        }
    }

    public XWPFDocument addNewLine(XWPFDocument doc){
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.addBreak();
        return doc;

    }

    public XWPFDocument setSeanceTitle(XWPFDocument doc ,int seanceTitle){
        XWPFParagraph p = doc.createParagraph();
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.setUnderline(UnderlinePatterns.SINGLE);
        r.setCapitalized(true);
        r.setFontFamily("Times New Roman");
        r.setItalic(true);
        r.setTextHighlightColor("Yellow");
        r.setBold(true);
        r.setText(toFrenchNumber(seanceTitle) +" séance");
        return  doc;
    }

    public String  toFrenchNumber(int number){
        switch (number){
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

            default: return "premierère"    ;
        }
    }
    public  void exportDocument(XWPFDocument doc,String title) throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(title+".docx");
            doc.write(out);
            out.close();
            doc.close();
        }catch (IOException e){
            logger.error(e.getMessage());

            throw new IOException("error with document " + title);
        }

    }
}
