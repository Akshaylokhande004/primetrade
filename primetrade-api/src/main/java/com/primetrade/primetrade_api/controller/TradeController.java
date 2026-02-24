package com.primetrade.primetrade_api.controller;

import com.primetrade.primetrade_api.dto.TradeRequest;
import com.primetrade.primetrade_api.dto.TradeResponse;
import com.primetrade.primetrade_api.service.TradeService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;


    @PostMapping
    public ResponseEntity<TradeResponse> createTrade(@Valid @RequestBody TradeRequest tradeRequest)
    {
        TradeResponse response =  tradeService.createTrade(tradeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public List<TradeResponse> getTrades()
    {

        return tradeService.getTrades();
    }
    @GetMapping("/{id}")
    public TradeResponse getTrade(@PathVariable Long id) {
        return tradeService.getTradeById(id);
    }
    @PutMapping("/{id}")
    public TradeResponse updateTrade(
            @PathVariable Long id,
            @Valid @RequestBody TradeRequest request) {
        return tradeService.updateTrade(id, request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
        return ResponseEntity.noContent().build();
    }



}
