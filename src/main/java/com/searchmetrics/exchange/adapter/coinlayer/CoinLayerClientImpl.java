package com.searchmetrics.exchange.adapter.coinlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;


@Service
public class CoinLayerClientImpl implements CoinLayerClient {

    @Value("${coinlayer.exchange-rate.access-key}")
    private String accessKey;

    @Value("${coinlayer.exchange-rate.latest}")
    private String latestRateUrl;

    @Value("${coinlayer.exchange-rate.historical}")
    private String historicalRateUrl;

    private RestTemplate restTemplate;

    private ObjectMapper mapper;

    public CoinLayerClientImpl(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }


    @Override
    public ExchangeRateResponse getLatestRate(String from, String to) {
        ResponseEntity<String> latestRate = restTemplate.getForEntity(getUriForLatestRate(from, to), String.class);
        return convertToExchangeRate(latestRate.getBody());
    }

    @Override
    public ExchangeRateResponse getHistoricalRate(String from, String to, LocalDate date) {
        ResponseEntity<String> historicalRate = restTemplate.getForEntity(getUriForHistoricRate(from, to, date), String.class);
        return convertToExchangeRate(historicalRate.getBody());
    }

    private URI getUriForLatestRate(String from, String to) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(latestRateUrl)
                .queryParam("access_key", accessKey)
                .queryParam("symbols", from + "," + to);
        return builder.buildAndExpand(builder).toUri();
    }

    private URI getUriForHistoricRate(String from, String to, LocalDate date) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(historicalRateUrl + date.toString())
                .queryParam("access_key", accessKey)
                .queryParam("symbols", from + "," + to);
        return builder.buildAndExpand(builder).toUri();
    }

    private ExchangeRateResponse convertToExchangeRate(String message) {
        ExchangeRateResponse response = new ExchangeRateResponse();
        try {
            response = mapper.readValue(message, ExchangeRateResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

}
