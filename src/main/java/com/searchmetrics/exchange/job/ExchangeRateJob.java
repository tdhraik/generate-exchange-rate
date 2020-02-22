package com.searchmetrics.exchange.job;


import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Configuration
public class ExchangeRateJob {

    private GenerateExchangeRateInteractor interactor;

    public ExchangeRateJob(GenerateExchangeRateInteractor interactor) {
        this.interactor = interactor;
    }


    @Scheduled(cron = "${cron.job.exchange.rate.latest}")
    public void getLatestExchangeRate() {
        System.out.println("Get latest exchange rate job started at " + LocalDateTime.now(ZoneId.of("Europe/Berlin")));
        interactor.getLatestExchangeRate();
    }
}
