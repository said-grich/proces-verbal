package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.AppString;
import com.procesverbal.procesverbal.dto.SeanceDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.AppString.TIMES_NEW_RAMAN_FONT;
import static com.procesverbal.procesverbal.helper.Functions.*;

@Service
public class SeanceService {


    @Autowired
    CommissionService commissionService;
    @Autowired
    JournalService journalService;
    @Autowired
    OfferService offerService;
    Logger logger = LoggerFactory.getLogger(DocumentService.class);

    public XWPFDocument creatSeance(XWPFDocument document, String aooNumber, SeanceDto seanceDto) throws IOException {
        try {


            document = creatHeaderOfDocument(document, seanceDto.getObjet(),aooNumber, seanceDto.getSeanceTitle());


            //set Commission Part:
            if (seanceDto.getIsHasCommission() == 1) {
                document = commissionService.setCommissionPart(document, seanceDto.getCommissionMemberDtoList(), seanceDto.getDateOfCommission(), aooNumber, seanceDto.getObjet(), seanceDto.getDecisionNumber(), seanceDto.getDecisionDate());

            }
            //cancels effect of page break
            if (seanceDto.getIsHasJournal() == 1) {
                document = journalService.setJournalPart(document, seanceDto.getJournalDtoList(), seanceDto.getDateOfPortail(), seanceDto.getHourOfPortail());
            }
            //setOffers:
            if (seanceDto.getIsHasOfferFirst() == 1) {
                document = offerService.setOffersPart1(document, seanceDto.getOfferDtoList(), seanceDto.getMontant());
            }
            if(seanceDto.getIsHasOfferSecond()==1){
               document = offerService.setOffersPart2(document,seanceDto.getOfferDtoList(),seanceDto.getMontant());
            }
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.addBreak(BreakType.PAGE);
            return document;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;

        }

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
            seanceNumberRun.setFontFamily(UNIVERS_57_CONDEDSED_FONT);
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



}
