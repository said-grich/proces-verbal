package com.procesverbal.procesverbal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDto {


    private String  journalName;
    private String  journalNumber;
    private String  journalDate;
    private String  journalPage;


}
