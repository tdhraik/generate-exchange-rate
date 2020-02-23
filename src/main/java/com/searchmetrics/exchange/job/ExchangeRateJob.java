package com.searchmetrics.exchange.job;


import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;


@Component
@RefreshScope
public class ExchangeRateJob {

    @Value("${cron.job.exchange.rate.latest}")
    private String cronExpLatestExchangeJob;

    private GenerateExchangeRateInteractor interactor;

    public ExchangeRateJob(GenerateExchangeRateInteractor interactor) {
        this.interactor = interactor;
    }

    public ScheduledFuture<?> triggerGetLatestExchangeRateJob(TaskScheduler scheduler) {
        return scheduler.schedule(
                () -> interactor.getLatestExchangeRate(),
                triggerContext -> new CronTrigger(cronExpLatestExchangeJob).nextExecutionTime(triggerContext));
    }
}
