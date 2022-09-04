package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.dto.CommissionMemberDto;
import com.procesverbal.procesverbal.dto.OfferDto;
import com.procesverbal.procesverbal.dto.SeanceDto;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.helper.Functions.*;

@Service
public class OfferFinancierService {

    @Autowired
    CommissionService commissionService;
    public XWPFDocument setOffersFinancierPart(XWPFDocument doc, Long montant, SeanceDto seanceDto) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText((capitalize(OFFER_TEXT_SEANCE_PUBLIC)));
        XWPFParagraph paragraph2 = doc.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setText((capitalize(OFFER_TEXT_MAJORATION_1)));
        XWPFParagraph paragraph3 = doc.createParagraph();
        XWPFRun run3 = paragraph3.createRun();
        run3.setFontFamily(TW_CEN_MT_FONT);
        run3.setText((capitalize(OFFER_TEXT_MAJORATION_2)));
        XWPFRun run4 = paragraph3.createRun();
        run4.setFontFamily(TW_CEN_MT_FONT);
        run4.setBold(true);
        run4.setText(NEANT);
        XWPFParagraph paragraph4 = doc.createParagraph();
        XWPFRun run5 = paragraph4.createRun();
        run5.setFontFamily(TW_CEN_MT_FONT);
        run5.setText(readTextFile(OFFER_TEXT_FINANCIER));
       List<OfferDto> offerDtoList = seanceDto.getOfferDtoList().stream().filter(offerDto -> offerDto.getIsRejectedBeforeMaj() == false).collect(Collectors.toList());
        doc = createThreeCaseTable(doc, OFFER_FINANCIER_TAB_HEADER, offerDtoList);
        doc = setMotifTab(doc, offerDtoList);
        doc = setRectifierPart(doc, offerDtoList, montant,seanceDto);
        doc=   setCommissionEmg( doc,  seanceDto.getCommissionMemberDtoListFinal(),seanceDto.getDateFaitLe());

        return doc;
    }

    XWPFTable initTab(XWPFDocument doc, int rowsNumber, int colsNumber) {

        XWPFTable tab = doc.createTable(rowsNumber, colsNumber);

        setTableBorderColor(tab, BLACK_COLOR);

        return tab;
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

    XWPFDocument createThreeCaseTable(XWPFDocument doc, List<String> header, List<OfferDto> offerDtoList) {
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
                    cell.setWidth("60%");
                } else if (j == 1) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getMontant() + "");
                    cell.setWidth("15%");
                } else if (j == 2) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getMajoration() + "%");
                    cell.setWidth("25%");
                }
            }
        }
        return doc;
    }

    XWPFDocument setMotifTab(XWPFDocument doc, List<OfferDto> offerDtoList) throws FileNotFoundException {
        offerDtoList = offerDtoList.stream().filter(offerDto -> offerDto.getIsRejectedAfterMaj()).collect(Collectors.toList());
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(capitalize(OFFER_TEXT_MOTIF_AFTER_1)));
        XWPFParagraph paragraph2 = doc.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setText(readTextFile(capitalize(OFFER_TEXT_MOTIF_AFTER_2)));
        if (!offerDtoList.isEmpty()) {
            doc = createTowCaseTable(doc, OFFER_MOTIF_TAB_HEADER, offerDtoList);

        } else {
            createTowCaseNeantTable(doc, OFFER_MOTIF_TAB_HEADER);
        }
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
                    nameRun.setText(offerDtoList.get(i - 1).getMotifAfterMaj());
                    cell.setWidth("50%");
                }
            }
        }
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

    XWPFDocument setRectifierPart(XWPFDocument doc, List<OfferDto> offerDtoList, Long montant,SeanceDto seanceDto) throws FileNotFoundException {
        offerDtoList.stream().filter(offerDto -> !offerDto.getIsRejectedBeforeMaj() && !offerDto.getIsRejectedBeforeMaj()).collect(Collectors.toList());
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(capitalize(OFFER_RECTIFIES_TEXT)));

        doc = createThreeCaseRecTable(doc, OFFER_RECTIFIES_TAB_HEADER, offerDtoList, montant);
        List<OfferDto> sortedList = offerDtoList.stream()
                .sorted(Comparator.comparingDouble(OfferDto::getMontantAfterMaj))
                .collect(Collectors.toList());
        doc = setClassementPart(doc,sortedList,seanceDto);

        return doc;
    }

    XWPFDocument createThreeCaseRecTable(XWPFDocument doc, List<String> header, List<OfferDto> offerDtoList, Long montant) {
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
                    cell.setWidth("60%");
                } else if (j == 1) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(offerDtoList.get(i - 1).getMontant() + "");
                    cell.setWidth("10%");
                } else if (j == 2) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    offerDtoList.get(i - 1).setMontantAfterMaj(montant + (montant * (offerDtoList.get(i - 1).getMajoration() / 100)));
                    nameRun.setText( offerDtoList.get(i - 1).getMontantAfterMaj() + "");
                    cell.setWidth("25%");
                }
            }
        }
        return doc;
    }

    XWPFDocument setClassementPart(XWPFDocument doc, List<OfferDto> offerDtoList,SeanceDto seanceDto) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(capitalize(OFFER_CLASSEMENT_TEXT)));

        XWPFParagraph paragraph2 = doc.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setText((capitalize(OFFER_CLASSEMENT_STRING)));

        doc = generateList(doc, offerDtoList);
        seanceDto.setOfferWinner(offerDtoList.get(0));
        doc =setWinner( doc , seanceDto.getOfferWinner(),seanceDto);

        return doc;
    }


    XWPFDocument generateList(XWPFDocument doc, List<OfferDto> offerDtoList) {
        for (int i = 1; i < offerDtoList.size() + 1; i++) {
            XWPFParagraph paragraph1 = doc.createParagraph();
            paragraph1.setIndentationFirstLine(PARAGRAPH_OFFSET);
            XWPFRun run1 = paragraph1.createRun();
            run1.setFontFamily(TW_CEN_MT_FONT);
            run1.setText(i + ".  ");
            XWPFRun run2 = paragraph1.createRun();
            run2.setFontFamily(TW_CEN_MT_FONT);
            run2.setBold(true);
            run2.setText(offerDtoList.get(i - 1).getName().toUpperCase());
            XWPFRun run3 = paragraph1.createRun();
            run3.setFontFamily(TW_CEN_MT_FONT);
            run3.setText("," + capitalize(offerDtoList.get(i - 1).getAddress()) + ".");
        }
        return doc;
    }

    XWPFDocument setWinner(XWPFDocument doc ,OfferDto offerWinner,SeanceDto seanceDto) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText((capitalize(OFFER_INVITE_STRING)));
        XWPFRun run2 = paragraph1.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setBold(true);
        run2.setText(offerWinner.getName().toUpperCase());
        XWPFRun run3 = paragraph1.createRun();
        run3.setFontFamily(TW_CEN_MT_FONT);
        run3.setText((capitalize(offerWinner.getAddress())) +"; à :");
        XWPFParagraph paragraph2 = doc.createParagraph();
        paragraph2.setIndentationFirstLine(800);
        XWPFRun run4 = paragraph2.createRun();
        run4.setFontFamily(TW_CEN_MT_FONT);
        run4.setText((capitalize(OFFER_INVITE_STRING_2)));

        //set Delai :
        String delaiString= OFFER_DELAI_STRING_1 +" "+ seanceDto.getDateDelai() +" à "+seanceDto.getHourDelai()+", "+OFFER_DELAI_STRING_2;
        XWPFParagraph paragraph3 = doc.createParagraph();
        XWPFRun run5 = paragraph3.createRun();
        run5.setFontFamily(TW_CEN_MT_FONT);
        run5.setBold(true);
        run5.setFontSize(12);
        run5.setText((capitalize(delaiString)));
        //set Suspend :
        XWPFParagraph paragraph4 = doc.createParagraph();
        XWPFRun run6 = paragraph4.createRun();
        run6.setFontFamily(TW_CEN_MT_FONT);
        run6.setText((capitalize(OFFER_SUSPEND_STRING)));

        XWPFRun run7 = paragraph4.createRun();
        run7.setBold(true);
        run7.setUnderline(UnderlinePatterns.SINGLE);
        run7.setFontFamily(TW_CEN_MT_FONT);
        run7.setText(" "+seanceDto.getDateSuspend() +" à "+ seanceDto.getHourSuspend()+" ");

        XWPFRun run8 = paragraph4.createRun();
        run8.setFontFamily(TW_CEN_MT_FONT);
        run8.setText(readTextFile(OFFER_SUSPEND_TEXT));

        //set fait le :




        return doc;
    }
    XWPFDocument setCommissionEmg(XWPFDocument doc, List<CommissionMemberDto> memberDtos ,String dateFaitLe){

        XWPFParagraph paragraph6 = doc.createParagraph();
        XWPFRun run9 = paragraph6.createRun();
        paragraph6.setAlignment(ParagraphAlignment.RIGHT);
        run9.setFontFamily(TW_CEN_MT_FONT);
        run9.setUnderline(UnderlinePatterns.SINGLE);
        String dateFaitLeString= OFFER_FAIT_LE_STRING+" " + dateFaitLe ;
        run9.setText(capitalize(dateFaitLeString));

        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setBold(true);
        run1.setUnderline(UnderlinePatterns.SINGLE);
        run1.setText((capitalize(OFFER_COMMISSION_LE_STRING)));

        commissionService.createTowCaseTable(doc,COMMISSION_TAB_HEADER,memberDtos);
        return doc;
    }




}
