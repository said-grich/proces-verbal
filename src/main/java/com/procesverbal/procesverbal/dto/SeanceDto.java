package com.procesverbal.procesverbal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<CommissionMemberDto> commissionMemberDtoList;
    private List<CommissionMemberDto> commissionMemberDtoListFinal;
    private List<JournalDto> journalDtoList;
    private List<OfferDto> offerDtoList;
    private OfferDto offerWinner;
    private String dateFaitLe;
    private String dateDelai;
    private String hourDelai;
    private String dateSuspend;
    private String hourSuspend;

    private int isHasCommission;
    private int isHasOfferFirst;
    private int isHasOfferSecond;
    private int isHasJournal;
    private int isHasReception;

    private String letterNumber;
    private String letterDate;
    private  String dateOfReception;
    private  String hourOfReception;

}
