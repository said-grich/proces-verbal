package com.procesverbal.procesverbal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private String title;
    private int seanceTitle;
    private String aooNumber;
    private String objet;
    private String  dateOfCommission;
    private String  decisionNumber;
    private String  decisionDate;
    private String  journalFrancais;
    private String  journalArab;
    private  String  dateAndHourOfPortail;

    private List<CommissionMemberDto> commissionMemberDtoList;
}
