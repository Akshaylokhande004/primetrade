package com.primetrade.primetrade_api.service;

import com.primetrade.primetrade_api.dto.TradeRequest;
import com.primetrade.primetrade_api.dto.TradeResponse;
import com.primetrade.primetrade_api.exception.CustomAccessDeniedException;
import com.primetrade.primetrade_api.exception.ResourceNotFoundException;
import com.primetrade.primetrade_api.model.Role;
import com.primetrade.primetrade_api.model.Trade;
import com.primetrade.primetrade_api.model.User;
import com.primetrade.primetrade_api.repository.TradeRepository;
import com.primetrade.primetrade_api.repository.UserRepository;

import lombok.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;
    private  final UserRepository userRepository;

    @CacheEvict(
            value = "trades",
            key = "'getTrades_' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"
    )
    public TradeResponse createTrade(TradeRequest request) {

        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Trade trade = Trade.builder()
                .assetsSymbol(request.getAssetsSymbol())
                .type(request.getType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .user(user)
                .build();

        tradeRepository.save(trade);

        return mapToResponse(trade);
    }

    @Cacheable(
            value = "trades",
            key = "#root.methodName + '_' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"
    )
    public List<TradeResponse> getTrades() {

        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        List<Trade> trades;

        if (user.getRole().name().equals("ADMIN")) {
            trades = tradeRepository.findAll();
        } else {
            trades = tradeRepository.findByUserId(user.getId());
        }

        return trades.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());


    }

    public TradeResponse getTradeById(Long id)
    {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Trade not found" ));

        enforceAccess(trade);
        return mapToResponse(trade);

    }

    private void enforceAccess(Trade trade) {

        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        if (user.getRole() == Role.ADMIN) {
            return; // Admin can access everything
        }

        if (!trade.getUser().getId().equals(user.getId())) {
            throw new CustomAccessDeniedException("Access denied");
        }
    }
    @CacheEvict(
            value = "trades",
            key = "'getTrades_' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"
    )
    public TradeResponse updateTrade(Long id, TradeRequest request) {

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trade not found"));

        enforceAccess(trade);

        trade.setAssetsSymbol(request.getAssetsSymbol());
        trade.setType(request.getType());
        trade.setQuantity(request.getQuantity());
        trade.setPrice(request.getPrice());

        tradeRepository.save(trade);

        return mapToResponse(trade);
    }
    @CacheEvict(
            value = "trades",
            key = "'getTrades_' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"
    )
    public void deleteTrade(Long id) {

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trade not found"));

        enforceAccess(trade);

        tradeRepository.delete(trade);
    }



    private String getCurrentUserEmail() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private TradeResponse mapToResponse(Trade trade) {
        return TradeResponse.builder()
                .id(trade.getId())
                .assetSymbol(trade.getAssetsSymbol())
                .type(trade.getType())
                .quantity(trade.getQuantity())
                .price(trade.getPrice())
                .timestamp(trade.getTimestamp())
                .ownerEmail(trade.getUser().getEmail())
                .build();
    }

}
