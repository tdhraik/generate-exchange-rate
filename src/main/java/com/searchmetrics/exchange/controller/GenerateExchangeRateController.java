package com.searchmetrics.exchange.controller;

import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import com.searchmetrics.exchange.job.HistoricalExchangeRateJob;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;


@RestController
@RequestMapping("generate/exchange")
@RefreshScope
public class GenerateExchangeRateController {

    private GenerateExchangeRateInteractor interactor;

    private HistoricalExchangeRateJob job;

    public GenerateExchangeRateController(GenerateExchangeRateInteractor interactor, HistoricalExchangeRateJob job) {
        this.interactor = interactor;
        this.job = job;
    }

    @GetMapping("/historic")
    public String populateHistoricalRates() {
        for(long i=1; i<=15; i++) {
            job.getHistoricalRateForGivenDate(LocalDate.now(ZoneId.of("Europe/Berlin")).minus(i, ChronoUnit.DAYS));
        }
        return "done";
    }

    @GetMapping("/latest")
    public String getLatestExchangeRate() {
        return interactor.getLatestExchangeRate();
    }
}
