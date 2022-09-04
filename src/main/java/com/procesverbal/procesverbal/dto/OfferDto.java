package com.procesverbal.procesverbal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private String name;
    private String address;
    private Boolean isOnline;
    private Boolean isRejectedBeforeMaj;
    private Boolean isRejectedAfterMaj;
    private String motifAfterMaj;
    private String motifBeforeMaj;
    private int isWithReserve;
    private String reserve;
    private Float montant;
    private Float montantAfterMaj;
    private Float majoration;


}
