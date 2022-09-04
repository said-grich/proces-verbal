package com.procesverbal.procesverbal.services;

import com.procesverbal.procesverbal.dto.SeanceDto;
import com.procesverbal.procesverbal.helper.FrenchNumberToWords;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

import static com.procesverbal.procesverbal.AppString.*;
import static com.procesverbal.procesverbal.helper.Functions.capitalize;
import static com.procesverbal.procesverbal.helper.Functions.readTextFile;

@Service
public class ReceptionService {
    @Autowired
    OfferFinancierService offerFinancierService;
    public XWPFDocument setReceptionPart(XWPFDocument doc, SeanceDto seanceDto) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        String  receptionText="Le "+seanceDto.getDateOfReception() +" à "+ seanceDto.getHourOfReception()+ readTextFile(RECEPTION_TEXT)+" "+seanceDto.getLetterNumber()+" du "+seanceDto.getLetterDate()+RECEPTION_STRING;
        run1.setText((capitalize(receptionText)));
        doc =seWinnerPart(doc,seanceDto);
        doc= offerFinancierService.setCommissionEmg(doc,seanceDto.getCommissionMemberDtoListFinal(),seanceDto.getDateFaitLe());
        return doc;
    }
    public XWPFDocument seWinnerPart(XWPFDocument doc, SeanceDto seanceDto) throws FileNotFoundException {
        XWPFParagraph paragraph1 = doc.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setFontFamily(TW_CEN_MT_FONT);
        run1.setText(readTextFile(WINNER_TEXT));
        XWPFRun run2 = paragraph1.createRun();
        run2.setFontFamily(TW_CEN_MT_FONT);
        run2.setBold(true);
        run2.setText(seanceDto.getOfferWinner().getName().toUpperCase());
        XWPFRun run3 = paragraph1.createRun();
        run3.setFontFamily(TW_CEN_MT_FONT);
        run3.setText(", " +seanceDto.getOfferWinner().getAddress());

        XWPFRun run4 = paragraph1.createRun();
        run4.setFontFamily(TW_CEN_MT_FONT);
        run4.setText(WINNER_STRING);
        //set winner montant
        XWPFRun run5 = paragraph1.createRun();
        run5.setBold(true);
        run5.setFontFamily(TW_CEN_MT_FONT);
        Float montantWinner=seanceDto.getOfferWinner().getMontant()+(seanceDto.getOfferWinner().getMontant()*(seanceDto.getOfferWinner().getMajoration()/100));
        Float montantNumber  = new Float(montantWinner ) ;
        int dh = (int)Math.floor( montantNumber ) ;
        int cent = (int)Math.floor( ( montantNumber - dh ) * 100.0f ) ;
        String montantString = FrenchNumberToWords.convert( dh ) + " et "
                + FrenchNumberToWords.convert( cent ) +" Centimes"  ;

        run5.setText(" "+montantWinner+" ("+montantString+" TTC).");

        return  doc;
    }



}
