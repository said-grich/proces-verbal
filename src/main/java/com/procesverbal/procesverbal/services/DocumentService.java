package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.dto.DocumentDto;
import com.procesverbal.procesverbal.dto.SeanceDto;
import com.procesverbal.procesverbal.entities.Document;
import com.procesverbal.procesverbal.repository.DocumentRepo;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.helper.Functions.*;

@Service
public class DocumentService {
    @Autowired
    SeanceService seanceService;
    @Autowired
    DocumentRepo documentRepo;
    Logger logger = LoggerFactory.getLogger(DocumentService.class);
    public String createDocument(DocumentDto documentDto) throws IOException {
        try {

            XWPFDocument document = new XWPFDocument();
            CTBody body = document.getDocument().getBody();
            if (!body.isSetSectPr()) {
                body.addNewSectPr();
            }

            CTSectPr section = body.getSectPr();
            if (!section.isSetPgSz()) {
                section.addNewPgSz();
            }
            CTPageSz pageSize = section.getPgSz();
            pageSize.setOrient(STPageOrientation.PORTRAIT);
            //A4 = 595x842 / multiply 20 since BigInteger represents 1/20 Point
            pageSize.setW(BigInteger.valueOf(11900));
            pageSize.setH(BigInteger.valueOf(16840));
            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(PAGE_MARGINS));
            pageMar.setTop(BigInteger.valueOf(PAGE_MARGINS_BOTTOM));
            pageMar.setRight(BigInteger.valueOf(PAGE_MARGINS));
            pageMar.setBottom(BigInteger.valueOf(PAGE_MARGINS_BOTTOM));
            pageMar.setFooter(BigInteger.valueOf(50));
            pageMar.setHeader(BigInteger.valueOf(10));
            List<SeanceDto> seanceDtoList = documentDto.getSeances();
            document = setFotterInfo(document,"AOO N° " + documentDto.getDocumentNumber());


            for (SeanceDto seanceDto:seanceDtoList){
                 document=   seanceService.creatSeance(document,documentDto.getDocumentNumber(),documentDto.getMontant(),seanceDto,documentDto.getObjet(),documentDto.getOfferWinner());
            }
            this.save(documentDto.getTitle());
             return exportDocument(document,documentDto.getTitle());

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;

        }
    }

    public XWPFDocument setFotterInfo(XWPFDocument doc, String aooNumber) {
        XWPFFooter footer = doc.createFooter(HeaderFooterType.DEFAULT);
        XWPFParagraph paragraph = footer.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run1 = paragraph.createRun();
        run1.setVerticalAlignment(VerticalAlignment.BOTTOM.name());
        run1.setUnderline(UnderlinePatterns.SINGLE);
        run1.setFontFamily(SEGOE_PRINT_FONT);
        run1.setFontSize(9);
        run1.setText(aooNumber);
        XWPFRun run2 = paragraph.createRun();
        run2.setFontFamily(SEGOE_PRINT_FONT);
        run2.setFontSize(9);
        run2.setText(HeaderFooter.page());

        return doc;
    }

    public void save(String title){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String path ="C:/proces-verbal-document/"+title+"/"+title+".docx";
        Document document =new Document(title,path,timestamp);
        System.out.println(documentRepo.save(document));

    }

    public ResponseEntity<List<Document>> findAll() {
        return new ResponseEntity<>(documentRepo.findAll(),HttpStatus.OK);
    }
}
