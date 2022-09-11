package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.dto.JournalDto;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.helper.Functions.*;

@Service
public class JournalService {

    XWPFDocument setJournalPart(XWPFDocument doc, List<JournalDto> journalDtos, String datePortail, String hourPortail) {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setText(capitalize(JOURNAUX_TEXT_1));
        run1.addBreak();
        XWPFParagraph paragraph2 = doc.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setBold(true);
        run2.setFontSize(11);
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setText(DOTE_SYMBOL + "  " + JOURNAUX_TEXT_2);
        doc = setJournalList(doc, journalDtos);
        doc = setPortaile(doc, datePortail, hourPortail);
        return doc;
    }

    XWPFDocument setJournalList(XWPFDocument doc, List<JournalDto> journalDtoList) {

        for (JournalDto journalDto : journalDtoList) {
            XWPFParagraph paragraph1 = doc.createParagraph();
            paragraph1.setIndentationFirstLine(PARAGRAPH_OFFSET);
            XWPFRun run1 = paragraph1.createRun();
            run1.setFontFamily(TW_CEN_MT_FONT);
            run1.setBold(true);
            run1.setText(DASH_SYMBOL+" "+journalDto.getJournalName().toUpperCase() +" : N°" + journalDto.getJournalNumber() + " du " + journalDto.getJournalDate() + " page: " + journalDto.getJournalPage());
        }
        return doc;
    }
    XWPFDocument setPortaile(XWPFDocument doc, String dateOfPortail, String hourOfPortail) {
        XWPFParagraph paragraph3 = doc.createParagraph();
        paragraph3.setIndentationFirstLine(PARAGRAPH_OFFSET);
        XWPFRun run3 = paragraph3.createRun();
        run3.setFontFamily(TW_CEN_MT_FONT);
        run3.setBold(true);
        run3.setText(capitalize(JOURNAUX_TEXT_PORTAIL) + " " + JOURNAUX_TEXT_PORTAIL_URL + " en date du " + dateOfPortail + " a " + hourOfPortail);
        run3.addBreak();
        return doc;
    }

}
