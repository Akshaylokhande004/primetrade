package com.primetrade.primetrade_api.repository;

import com.primetrade.primetrade_api.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TradeRepository  extends JpaRepository<Trade, Long> {
    List<Trade> findByUserId(Long id);
}
