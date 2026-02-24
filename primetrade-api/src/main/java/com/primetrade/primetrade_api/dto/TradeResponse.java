package com.primetrade.primetrade_api.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
public class TradeResponse {
    private Long id;
    private String assetSymbol;
    private String type;
    private Double quantity;
    private Double price;
    private LocalDateTime timestamp;
    private String ownerEmail;
}
