package com.searchmetrics.exchange.controller;

import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import com.searchmetrics.exchange.job.HistoricalExchangeRateJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;


@RestController
@RequestMapping("generate/exchange")
public class GenerateExchangeRateController {

    @Autowired
    private GenerateExchangeRateInteractor interactor;

    @Autowired
    private HistoricalExchangeRateJob job;

    @GetMapping("/historic")
    public String ping() {
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
