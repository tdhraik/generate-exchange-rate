package com.searchmetrics.exchange.job;


import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
public class ExchangeRateJob {

    private GenerateExchangeRateInteractor interactor;

    public ExchangeRateJob(GenerateExchangeRateInteractor interactor) {
        this.interactor = interactor;
    }


    @Scheduled(cron = "${cron.job.exchange.rate.latest}")
    public void getLatestExchangeRate() {
        interactor.getLatestExchangeRate();
    }
}
