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
    private String aooNumber;
    private  Long montant;
    private  List<SeanceDto> seanceDtoList;
}
