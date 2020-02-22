package com.searchmetrics.exchange.adapter.coinlayer;


import java.time.LocalDate;

public interface CoinLayerClient {

    ExchangeRateResponse getLatestRate(String from, String to);

    ExchangeRateResponse getHistoricalRate(String from, String to, LocalDate date);
}
