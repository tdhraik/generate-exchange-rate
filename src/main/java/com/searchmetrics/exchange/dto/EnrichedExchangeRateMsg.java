package com.searchmetrics.exchange.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.searchmetrics.exchange.adapter.coinlayer.ExchangeRateResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrichedExchangeRateMsg {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
    private long timestamp;

    public static String toEnrichedExchangeRateMsg(ExchangeRateResponse rateResponse, String fromCurrency, String toCurrency) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            EnrichedExchangeRateMsg msg = new EnrichedExchangeRateMsg(fromCurrency, toCurrency,
                    new BigDecimal(rateResponse.getRates().get(fromCurrency)).setScale(2, RoundingMode.CEILING),
                    rateResponse.getTimestamp());
            return mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            e.getStackTrace();
            throw new RuntimeException();
        }
    }
}