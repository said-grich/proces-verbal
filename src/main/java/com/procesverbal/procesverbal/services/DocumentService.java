package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.AppString;
import com.procesverbal.procesverbal.dto.CommissionMemberDto;
import com.procesverbal.procesverbal.dto.DocumentDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.procesverbal.procesverbal.AppString.*;

@Service
public class DocumentService {
    Logger logger = LoggerFactory.getLogger(DocumentService.class);

    public void createDocument(DocumentDto documentDto) throws IOException {
        try {
            XWPFDocument document = new XWPFDocument();
            CTBody body = document.getDocument().getBody();
            if(!body.isSetSectPr()){
                body.addNewSectPr();
            }

            CTSectPr section = body.getSectPr();
            if(!section.isSetPgSz()){
                section.addNewPgSz();
            }
            CTPageSz pageSize = section.getPgSz();
            pageSize.setOrient(STPageOrientation.PORTRAIT);
            //A4 = 595x842 / multiply 20 since BigInteger represents 1/20 Point
            pageSize.setW(BigInteger.valueOf(16840));
            pageSize.setH(BigInteger.valueOf(11900));

            document = creatHeaderOfDocument(document, documentDto.getObjet(), documentDto.getAooNumber(), documentDto.getSeanceTitle());


            document = setCommissionPart(document,documentDto.getCommissionMemberDtoList(),documentDto.getDateOfCommission(),documentDto.getAooNumber(),documentDto.getObjet());
            exportDocument(document, documentDto.getTitle());

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;

        }

    }

    public XWPFDocument setCommissionPart(XWPFDocument doc,List<CommissionMemberDto> memberDtos ,String dateOfCommission,String aooNumber,String objet) {
        try {
            XWPFParagraph commissionParagraph1 = doc.createParagraph();
            XWPFRun commissionRun1 = commissionParagraph1.createRun();
            commissionRun1.setBold(true);
            commissionRun1.setText("Le " + dateOfCommission);
            commissionRun1.setFontFamily(GARAMOND_FONT);
            commissionRun1.setFontSize(12);
            XWPFRun commissionRun2 = commissionParagraph1.createRun();
            commissionRun2.setFontSize(11);
            commissionRun2.setFontFamily(GARAMOND_FONT);
            commissionRun2.setText(readTextFile(COMMISSION_TEXT));
            commissionRun2.addBreak();
            //commission table
            List<CommissionMemberDto>  list =new ArrayList<>();
            list.addAll(COMMISSION_FIX_MEMBER);
            list.addAll(memberDtos);
            doc = createTowCaseTable(doc,COMMISSION_TAB_HEADER,list);
            //ayent pour pbjet :
            XWPFParagraph commissionObjetParagraphe = doc.createParagraph();
            commissionObjetParagraphe.setIndentationFirstLine(720);
            commissionObjetParagraphe.setSpacingAfterLines(1);
            commissionObjetParagraphe.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun objetRun = commissionObjetParagraphe.createRun();
            objetRun.setFontSize(11);
            objetRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
            objetRun.setText(capitalize(readTextFile(COMMISSION_TEXT_1)));
            //Aoo number
            XWPFRun objetNumberRun = commissionObjetParagraphe.createRun();
            objetNumberRun.setFontSize(11);
            objetNumberRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
            objetNumberRun.setBold(true);
            objetNumberRun.setText(aooNumber.toUpperCase());
            //static text
            XWPFRun staticRun = commissionObjetParagraphe.createRun();
            staticRun.setFontSize(11);
            staticRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
            staticRun.setText(" ayant pour objet :");
            //set objet text
            XWPFRun objetTextRun = commissionObjetParagraphe.createRun();
            objetTextRun.setFontSize(11);
            objetTextRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
            objetTextRun.setBold(true);
            objetTextRun.setText(objet.toUpperCase());

            return doc;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + "error with file " + COMMISSION_TEXT);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    XWPFDocument createTowCaseTable(XWPFDocument doc , List<String> header, List<CommissionMemberDto> commissionMemberDtoList) {
        int rows= commissionMemberDtoList.size()+1;
        int cols=header.size();
        XWPFTable table =initTab(doc,rows,cols);
        table=setTabHeader(table,header);
        for(int i =1 ;i<rows;i++){
            XWPFTableRow row = table.getRow(i);
            row.setHeight(1300);
            for (int j =0 ; j<cols; j++){
                if(j==0){
                    XWPFTableCell cell=row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph= cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun nameRun= tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(commissionMemberDtoList.get(i-1).getName().toUpperCase());
                    XWPFRun descRun= tmpParagraph.createRun();
                    descRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    descRun.setFontSize(12);
                    descRun.setText(", "+capitalize(commissionMemberDtoList.get(i-1).getDesc()));
                    XWPFRun memberTypeRun= tmpParagraph.createRun();
                    memberTypeRun.setBold(true);
                    memberTypeRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    memberTypeRun.setFontSize(12);
                    memberTypeRun.setText(", "+commissionMemberDtoList.get(i-1).getRole().toUpperCase());
                    cell.setWidth("65%");
                }else if(j==1){
                    XWPFTableCell cell=row.getCell(j);
                    XWPFParagraph tmpParagraph= cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun= tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(commissionMemberDtoList.get(i-1).getEmargement());
                    cell.setWidth("35%");
                }
            }
        }
        doc=addNewLine(doc);
        // First row
        return doc;
    }
    XWPFTable setTabHeader(XWPFTable table,List<String> headerList){
        XWPFTableRow row = table.getRow(0);
        row.setHeight(800);
        for(int j =0 ; j<headerList.size();j++){
            XWPFTableCell cell=row.getCell(j);
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            XWPFParagraph tmpParagraph= cell.getParagraphs().get(0);
            tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun nameRun= tmpParagraph.createRun();
            nameRun.setBold(true);
            nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
            nameRun.setFontSize(12);
            nameRun.setText(capitalize(headerList.get(j)));
            if (j == 0) {
                cell.setWidth("65%");
            } else {
                cell.setWidth("35%");
            }
        }

        return  table;
    }
    XWPFTable initTab(XWPFDocument doc,int rowsNumber,int colsNumber){

        XWPFTable tab = doc.createTable(rowsNumber,colsNumber);
        tab.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE,BORDER_SIZE,0,BLACK_COLOR);
        tab.setTopBorder(XWPFTable.XWPFBorderType.SINGLE,BORDER_SIZE,0,BLACK_COLOR);
        tab.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE,BORDER_SIZE,0,BLACK_COLOR);
        tab.setRightBorder(XWPFTable.XWPFBorderType.SINGLE,BORDER_SIZE,0,BLACK_COLOR);


        return tab;
    }
    public XWPFDocument creatHeaderOfDocument(XWPFDocument doc, String objet, String seanceNumber, int seanceTitle) {
        try {
            //set logo image
            doc = addImagesToWordDocument(doc, AppString.LOGO);
            //set under line image
            doc = addImagesToWordDocument(doc, AppString.UNDER_LINE);
            //set Aoo number
            XWPFParagraph seanceNumberParagraph = doc.createParagraph();
            seanceNumberParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun seanceNumberRun = seanceNumberParagraph.createRun();
            seanceNumberRun.setBold(true);
            seanceNumberRun.setFontSize(11);
            seanceNumberRun.setFontFamily(UNIVERS_57_CONDEDSED);
            seanceNumberRun.setText("AOO N° " + seanceNumber.toUpperCase());
            seanceNumberRun.setUnderline(UnderlinePatterns.SINGLE);
            seanceNumberRun.addBreak();
            //set object paragraph
            XWPFParagraph ObjetParagraph = doc.createParagraph();
            ObjetParagraph.setIndentationFirstLine(720);
            XWPFRun objetRun1 = ObjetParagraph.createRun();
            objetRun1.setFontSize(11);
            objetRun1.setUnderline(UnderlinePatterns.SINGLE);
            objetRun1.setFontFamily(TIMES_NEW_RAMAN_FONT);
            objetRun1.setText(readTextFile(AppString.OBJET_TEXT));
            XWPFRun objetRun2 = ObjetParagraph.createRun();
            objetRun2.setFontSize(12);
            objetRun2.setBold(true);
            objetRun2.setFontFamily(TIMES_NEW_RAMAN_FONT);
            objetRun2.setText(objet.toUpperCase());
            objetRun2.addBreak();
            doc = setSeanceTitle(doc, seanceTitle);
            return doc;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public XWPFDocument addImagesToWordDocument(XWPFDocument doc, String imagePath) throws IOException {
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
            return doc;
        } catch (IOException e) {
            logger.error(e.getMessage());

            throw new IOException("error with image " + imagePath);

        } catch (InvalidFormatException e) {
            logger.error(e.getMessage());
            throw new IOException("error with image " + imagePath);

        }
    }

    public String readTextFile(String textPath) throws FileNotFoundException {
        try {
            File myObj = new File(textPath);
            String data = "";
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
            return data;
        } catch (FileNotFoundException e) {

            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new FileNotFoundException("Error with file " + textPath);
        }
    }

    public XWPFDocument addNewLine(XWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.addBreak();
        return doc;

    }

    public XWPFDocument setSeanceTitle(XWPFDocument doc, int seanceTitle) {
        XWPFParagraph p = doc.createParagraph();
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.setUnderline(UnderlinePatterns.SINGLE);
        r.setFontFamily(TIMES_NEW_RAMAN_FONT);
        r.setItalic(true);
        r.setTextHighlightColor("Yellow");
        r.setBold(true);
        r.setText(capitalize(toFrenchNumber(seanceTitle)) + " séance");
        r.addBreak();
        return doc;
    }

    public String toFrenchNumber(int number) {
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

    public void exportDocument(XWPFDocument doc, String title) throws IOException {
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

}
