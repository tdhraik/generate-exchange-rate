package com.searchmetrics.exchange;

import com.searchmetrics.exchange.job.HistoricalExchangeRateJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class GenerateExchangeRateApp {

    @Autowired
    private HistoricalExchangeRateJob job;

    public static void main(String[] args) {
        SpringApplication.run(GenerateExchangeRateApp.class, args);
    }

    // TODO: remove once initial historical data is fetched
    @PostConstruct
    public void getHistoricRates() {
        for(long i=1; i<=1; i++) {
                job.getHistoricalRateForGivenDate(LocalDate.now(ZoneId.of("Europe/Berlin")).minus(i, ChronoUnit.DAYS));
        }
    }
}
