package com.procesverbal.procesverbal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeanceDto {
    private int seanceTitle;



    private String objet;
    private String  dateOfCommission;
    private String  decisionNumber;
    private String  decisionDate;
    private  String dateOfPortail;
    private  String hourOfPortail;
    private  String montant;
    private List<CommissionMemberDto> commissionMemberDtoList;
    private List<JournalDto> journalDtoList;
    private List<OfferDto> offerDtoList;

    private int isHasCommission;
    private int isHasOfferFirst;
    private int isHasOfferSecond;
    private int isHasJournal;
}
