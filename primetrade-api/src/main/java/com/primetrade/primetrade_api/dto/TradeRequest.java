package com.primetrade.primetrade_api.dto;

import jakarta.validation.constraints.*;

import lombok.*;


@Getter
@Setter
public class TradeRequest {
    @NotBlank
    private String assetsSymbol;

    @NotBlank
    private String type; // BUY / SELL

    @Positive
    private Double quantity;

    @Positive
    private Double price;
}
