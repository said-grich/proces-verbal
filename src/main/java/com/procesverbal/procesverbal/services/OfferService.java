package com.procesverbal.procesverbal.services;


import com.procesverbal.procesverbal.dto.CommissionMemberDto;
import com.procesverbal.procesverbal.dto.OfferDto;
import com.procesverbal.procesverbal.helper.FrenchNumberToWords;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.helper.Functions.*;

@Service
public class OfferService {
    Logger logger = LoggerFactory.getLogger(DocumentService.class);
    XWPFDocument setOffersPart1(XWPFDocument doc , List<OfferDto> offerDtoList, String montant) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setText(readTextFile(OFFER_TEXT_PLIS));
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.addBreak();
        doc=setOfferLocal(doc,offerDtoList);
        doc=setOfferOnline(doc,offerDtoList);
        doc=setNonDeposer(doc);
        doc=setIncomplete(doc);
        doc=setAllOffersList(doc,offerDtoList);
        return  doc;
    }
    XWPFDocument setOfferLocal(XWPFDocument doc, List<OfferDto> offerDtoList){
        List<OfferDto> listFinal= offerDtoList.stream()
                .filter(offer -> offer.getIsOnline() == false)
                .collect(Collectors.toList());
      doc=  generateList( doc,  listFinal);

        return doc;
    }
    XWPFDocument setOfferOnline(XWPFDocument doc, List<OfferDto> offerDtoList) throws FileNotFoundException {
        List<OfferDto> listFinal= offerDtoList.stream()
                .filter(offer -> offer.getIsOnline() == true)
                .collect(Collectors.toList());
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(capitalize(readTextFile(OFFER_TEXT_ONLINE)));
        XWPFRun run2 = paragraph1.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setBold(true);
        run2.setText(listFinal.size()+" offres reçues.");

        return doc;
    }


    XWPFDocument generateList(XWPFDocument doc, List<OfferDto> offerDtoList) {
        for (int i =1 ; i<offerDtoList.size()+1 ;i++) {
            XWPFParagraph paragraph1 = doc.createParagraph();
            paragraph1.setIndentationFirstLine(PARAGRAPH_OFFSET);
            XWPFRun run1 = paragraph1.createRun();
            run1.setFontFamily(TW_CEN_MT_FONT);
            run1.setText(i+".  ");
            XWPFRun run2 = paragraph1.createRun();
            run2.setFontFamily(TW_CEN_MT_FONT);
            run2.setBold(true);
            run2.setText(offerDtoList.get(i-1).getName().toUpperCase());
            XWPFRun run3 = paragraph1.createRun();
            run3.setFontFamily(TW_CEN_MT_FONT);
            run3.setText(","+capitalize(offerDtoList.get(i-1).getAddress())+".");
        }
        return doc;
    }
    XWPFDocument setNonDeposer(XWPFDocument doc) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(OFFER_TEXT_NON_DEPOSE));
        XWPFRun run2 = paragraph1.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setBold(true);
        run2.setText(NEANT+".");
        run2.addBreak();
        return doc;
    }
    XWPFDocument setIncomplete(XWPFDocument doc) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(OFFER_TEXT_INCOMPLETE));
        XWPFRun run2 = paragraph1.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setBold(true);
        run2.setText(NEANT+".");
        run2.addBreak();
        return doc;
    }
    XWPFDocument setAllOffersList(XWPFDocument doc,List<OfferDto> offerDtoList) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(OFFER_TEXT_PLIS_DESPOSER));
        doc=  generateList(doc,offerDtoList);
        return doc;
    }

    //offer Part 2 :
    XWPFDocument setOffersPart2(XWPFDocument doc , List<OfferDto> offerDtoList, String montant) throws FileNotFoundException {
        doc=setMontantPart(doc,montant);
        doc=setStaticTextOfferMotifs(doc);
        doc=setMotifTab(doc,offerDtoList);
        doc=setListDesOffersWithoutReserve(doc,offerDtoList);
        doc=setListDesOffersWithReserve(doc,offerDtoList);

        return doc;
    }
    XWPFDocument setListDesOffersWithoutReserve(XWPFDocument doc,List<OfferDto> offerDtoList ) throws FileNotFoundException {
        offerDtoList=offerDtoList.stream().filter(offerDto -> (offerDto.getIsRejectedBeforeMaj()==false && offerDto.getIsWithReserve()!=1) ).collect(Collectors.toList());
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(capitalize(readTextFile(OFFER_TEXT_WITHOUT_RS_TXT)));
        XWPFRun run2 = paragraph1.createRun();
        run2.setUnderline(UnderlinePatterns.SINGLE);
        run2.setText(capitalize(OFFER_TEXT_WITHOUT_RS_STRING));
        doc=  generateList(doc,offerDtoList);
        return doc;
    }

    XWPFDocument setListDesOffersWithReserve(XWPFDocument doc,List<OfferDto> offerDtoList) {
        offerDtoList=offerDtoList.stream().filter(offerDto -> (offerDto.getIsRejectedBeforeMaj()==false && offerDto.getIsWithReserve()==1) ).collect(Collectors.toList());
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(capitalize(OFFER_TEXT_WITH_RS_STRING));
        if(!offerDtoList.isEmpty()){
            doc =createTowCaseReserveTable(doc,OFFER_RESERVE_TAB_HEADER,offerDtoList);

        }else {
            doc =createTowCaseNeantTable(doc,OFFER_RESERVE_TAB_HEADER);
        }
        return  doc;
    }
    XWPFDocument setMontantPart(XWPFDocument doc,String montant) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(OFFER_TEXT_MONTANT));
        XWPFRun run2 = paragraph1.createRun();
        run2.setBold(true);
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setText(" "+montant+DH_TTC_SYMBOL);
        Float montantNumber  = new Float( montant ) ;
        int dh = (int)Math.floor( montantNumber ) ;
        int cent = (int)Math.floor( ( montantNumber - dh ) * 100.0f ) ;
        String montantString = FrenchNumberToWords.convert( dh ) + " et "
                + FrenchNumberToWords.convert( cent ) +" Centimes"  ;
        XWPFRun run3 = paragraph1.createRun();
        run3.setFontFamily(TW_CEN_MT_FONT);
        run3.setText(" ("+montantString+" TTC).");
        return  doc;
    }
    XWPFDocument setStaticTextOfferMotifs(XWPFDocument doc) throws FileNotFoundException {
        List<String> partsList=new ArrayList<>(){{
            add(OFFER_TEXT_MOTIF_PART1);
            add(OFFER_TEXT_MOTIF_PART2);
            add(OFFER_TEXT_MOTIF_PART3);
            add(OFFER_TEXT_MOTIF_PART4);
        }};
        for(String part:partsList){
            XWPFParagraph paragraph1 = doc.createParagraph();
            XWPFRun run1 = paragraph1.createRun();
            run1.setFontFamily(TW_CEN_MT_FONT);
            run1.setText(readTextFile(part));
        }
        return doc;
    }

    XWPFDocument setMotifTab(XWPFDocument doc,List<OfferDto> offerDtoList){
        offerDtoList=offerDtoList.stream().filter(offerDto -> offerDto.getIsRejectedBeforeMaj()).collect(Collectors.toList());
        doc =createTowCaseTable(doc,OFFER_MOTIF_TAB_HEADER,offerDtoList);
        return doc;
    }

    XWPFDocument createTowCaseTable(XWPFDocument doc, List<String> header, List<OfferDto> offerDtoList) {
        int rows = offerDtoList.size() + 1;
        int cols = header.size();
        XWPFTable table = initTab(doc, rows, cols);
        table = setTabHeader(table, header);
        for (int i = 1; i < rows; i++) {
            XWPFTableRow row = table.getRow(i);
            row.setHeight(1300);
            for (int j = 0; j < cols; j++) {
                if (j == 0) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.LEFT);
                    //set Name
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getName().toUpperCase());
                    //set Address
                    XWPFRun addressRun = tmpParagraph.createRun();
                    addressRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    addressRun.setFontSize(12);
                    addressRun.setText(", " + offerDtoList.get(i - 1).getAddress());
                    cell.setWidth("50%");
                } else if (j == 1) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getMotifBeforeMaj());
                    cell.setWidth("50%");
                }
            }
        }
        // First row
        return doc;
    }

    XWPFTable setTabHeader(XWPFTable table, List<String> headerList) {
        XWPFTableRow row = table.getRow(0);
        row.setHeight(800);
        for (int j = 0; j < headerList.size(); j++) {
            XWPFTableCell cell = row.getCell(j);
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
            tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun nameRun = tmpParagraph.createRun();
            nameRun.setBold(true);
            nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
            nameRun.setFontSize(12);
            nameRun.setText(capitalize(headerList.get(j)));
            if (j == 0) {
                cell.setWidth("50%");
            } else {
                cell.setWidth("50%");
            }
        }

        return table;
    }

    XWPFTable initTab(XWPFDocument doc, int rowsNumber, int colsNumber) {

        XWPFTable tab = doc.createTable(rowsNumber, colsNumber);

        setTableBorderColor(tab, BLACK_COLOR);

        return tab;
    }


    //reserve TAB:

    XWPFDocument createTowCaseReserveTable(XWPFDocument doc, List<String> header, List<OfferDto> offerDtoList) {
        int rows = offerDtoList.size() + 1;
        int cols = header.size();
        XWPFTable table = initTab(doc, rows, cols);
        table = setTabHeader(table, header);
        for (int i = 1; i < rows; i++) {
            XWPFTableRow row = table.getRow(i);
            row.setHeight(CELL_HEIGHT_CONTENT);
            for (int j = 0; j < cols; j++) {
                if (j == 0) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.LEFT);
                    //set Name
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getName().toUpperCase());
                    //set Address
                    XWPFRun addressRun = tmpParagraph.createRun();
                    addressRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    addressRun.setFontSize(12);
                    addressRun.setText(", " + offerDtoList.get(i - 1).getAddress());
                    cell.setWidth("50%");
                } else if (j == 1) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getReserve());
                    cell.setWidth("50%");
                }
            }
        }
        // First row
        return doc;
    }

    XWPFDocument createTowCaseNeantTable(XWPFDocument doc, List<String> header) {
        int rows = 2;
        int cols = header.size();
        XWPFTable table = initTab(doc, rows, cols);
        table.setWidth("100%");
        table = setTabHeader(table, header);
        for (int i = 1; i < rows; i++) {
            XWPFTableRow row = table.getRow(i);
            row.setHeight(CELL_HEIGHT_CONTENT);
            for (int j = 0; j < cols; j++) {
                if (j == 0) {

                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    //set Name
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(NEANT);
                    cell.setWidth("50%");
                } else if (j == 1) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(NEANT);
                    cell.setWidth("50%");
                }
            }
        }
        // First row
        return doc;
    }


}
