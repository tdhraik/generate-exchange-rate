package com.searchmetrics.exchange.job;

import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledFuture;

@Component
@RefreshScope
public class HistoricalExchangeRateJob {

    @Value("${cron.job.exchange.rate.historic}")
    private String cronExpHistoricExchangeJob;

    private GenerateExchangeRateInteractor interactor;

    public HistoricalExchangeRateJob(GenerateExchangeRateInteractor interactor) {
        this.interactor = interactor;
    }

    public ScheduledFuture<?> triggerHistoricLatestExchangeRateJob(TaskScheduler scheduler) {
        return scheduler.schedule(
                () -> interactor.getHistoricalExchangeRate(LocalDate.now().minus(1, ChronoUnit.DAYS)),
                triggerContext -> new CronTrigger(cronExpHistoricExchangeJob).nextExecutionTime(triggerContext)
        );
    }

    public void getHistoricalRateForGivenDate(LocalDate date) {
        interactor.getHistoricalExchangeRate(date);
    }
}
