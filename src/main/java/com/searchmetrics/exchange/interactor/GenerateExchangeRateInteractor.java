package com.searchmetrics.exchange.interactor;

import com.searchmetrics.exchange.adapter.coinlayer.CoinLayerClient;
import com.searchmetrics.exchange.adapter.coinlayer.ExchangeRateResponse;
import com.searchmetrics.exchange.adapter.kafka.ExchangeRateProducer;
import com.searchmetrics.exchange.dto.EnrichedExchangeRateMsg;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GenerateExchangeRateInteractor {

    private static final String FROM_CURRENCY = "BTC";
    private static final String TO_CURRENCY = "USD";

    private CoinLayerClient client;

    private ExchangeRateProducer producer;


    public GenerateExchangeRateInteractor(CoinLayerClient client, ExchangeRateProducer producer) {
        this.client = client;
        this.producer = producer;
    }

    public String getLatestExchangeRate() {
        ExchangeRateResponse response = client.getLatestRate(FROM_CURRENCY, TO_CURRENCY);
        producer.sendLatestExchangeRateMsg(response, FROM_CURRENCY, TO_CURRENCY);
        return EnrichedExchangeRateMsg.toEnrichedExchangeRateMsg(response, FROM_CURRENCY, TO_CURRENCY);
    }

    public void getHistoricalExchangeRate(LocalDate date) {
        ExchangeRateResponse response = client.getHistoricalRate(FROM_CURRENCY, TO_CURRENCY, date);
        producer.sendHistoricExchangeRateMsg(response, FROM_CURRENCY, TO_CURRENCY);
    }
}
