package com.searchmetrics.exchange.job;

import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Configuration
public class HistoricalExchangeRateJob {

    private GenerateExchangeRateInteractor interactor;

    public HistoricalExchangeRateJob(GenerateExchangeRateInteractor interactor) {
        this.interactor = interactor;
    }

    @Scheduled(cron = "${cron.job.exchange.rate.historic}", zone = "Europe/Berlin")
    public void getHistoricalRateForPreviousDay() {
        interactor.getHistoricalExchangeRate(LocalDate.now().minus(1, ChronoUnit.DAYS));
    }

    public void getHistoricalRateForGivenDate(LocalDate date) {
        interactor.getHistoricalExchangeRate(date);
    }
}
