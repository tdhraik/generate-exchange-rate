package com.searchmetrics.exchange.adapter.coinlayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

    @JsonProperty("target")
    private String target;

    @JsonProperty("rates")
    private Map<String, String> rates;

    @JsonProperty("timestamp")
    private long timestamp;
}
