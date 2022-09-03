package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.dto.CommissionMemberDto;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.helper.Functions.*;

@Service
public class CommissionService {
    Logger logger = LoggerFactory.getLogger(DocumentService.class);
    public XWPFDocument setCommissionPart(XWPFDocument doc, List<CommissionMemberDto> memberDtos, String dateOfCommission, String aooNumber, String objet, String decisionNumber, String dateOfDecision) {
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
            XWPFRun commissionRun3 = commissionParagraph1.createRun();
            commissionRun3.setFontSize(11);
            commissionRun3.setBold(true);
            commissionRun3.setFontFamily(GARAMOND_FONT);
            commissionRun3.setText(" " + decisionNumber + " du " + dateOfDecision + " ");
            XWPFRun commissionRun4 = commissionParagraph1.createRun();
            commissionRun4.setFontSize(11);

            commissionRun4.setFontFamily(GARAMOND_FONT);
            commissionRun4.setText(COMMISSION_TEXT_2);
            //commission table
            List<CommissionMemberDto> list = new ArrayList<>();
            list.addAll(COMMISSION_FIX_MEMBER);
            list.addAll(memberDtos);
            doc = createTowCaseTable(doc, COMMISSION_TAB_HEADER, list);
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
            objetTextRun.setText(objet.toUpperCase() + ".");

            return doc;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + "error with file " + COMMISSION_TEXT);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    XWPFDocument createTowCaseTable(XWPFDocument doc, List<String> header, List<CommissionMemberDto> commissionMemberDtoList) {
        int rows = commissionMemberDtoList.size() + 1;
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
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText(commissionMemberDtoList.get(i - 1).getName().toUpperCase());
                    XWPFRun descRun = tmpParagraph.createRun();
                    descRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    descRun.setFontSize(12);
                    descRun.setText(", " + capitalize(commissionMemberDtoList.get(i - 1).getDesc()));
                    XWPFRun memberTypeRun = tmpParagraph.createRun();
                    memberTypeRun.setBold(true);
                    memberTypeRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    memberTypeRun.setFontSize(12);
                    memberTypeRun.setText(", " + commissionMemberDtoList.get(i - 1).getRole().toUpperCase());
                    cell.setWidth("65%");
                } else if (j == 1) {
                    XWPFTableCell cell = row.getCell(j);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
                    tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun nameRun = tmpParagraph.createRun();
                    nameRun.setBold(true);
                    nameRun.setFontFamily(TIMES_NEW_RAMAN_FONT);
                    nameRun.setFontSize(12);
                    nameRun.setText("--");
                    cell.setWidth("35%");
                }
            }
        }
        // First row
        return doc;
    }

    XWPFTable setTabHeader(XWPFTable table, List<String> headerList) {
        XWPFTableRow row = table.getRow(0);
        row.setHeight(CELL_HEIGHT_HEADER);
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
                cell.setWidth("65%");
            } else {
                cell.setWidth("35%");
            }
        }

        return table;
    }

    XWPFTable initTab(XWPFDocument doc, int rowsNumber, int colsNumber) {

        XWPFTable tab = doc.createTable(rowsNumber, colsNumber);

        setTableBorderColor(tab, BLACK_COLOR);

        return tab;
    }
}
