package com.searchmetrics.exchange.config;

import com.searchmetrics.exchange.job.ExchangeRateJob;
import com.searchmetrics.exchange.job.HistoricalExchangeRateJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledFuture;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);

    private ScheduledFuture<?> latestJobFuture;
    private ScheduledFuture<?> historicalJobFuture;
    private ScheduledTaskRegistrar taskRegistrar;

    private ExchangeRateJob exchangeRateJob;
    private HistoricalExchangeRateJob historicalExchangeRateJob;

    public SchedulerConfig(ExchangeRateJob exchangeRateJob, HistoricalExchangeRateJob historicalExchangeRateJob) {
        this.exchangeRateJob = exchangeRateJob;
        this.historicalExchangeRateJob = historicalExchangeRateJob;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler =new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
        latestJobFuture = exchangeRateJob.triggerGetLatestExchangeRateJob(threadPoolTaskScheduler);
        historicalJobFuture = historicalExchangeRateJob.triggerHistoricLatestExchangeRateJob(threadPoolTaskScheduler);
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        this.taskRegistrar = scheduledTaskRegistrar;
    }

    public void cancelLatestExchangeJob() {
        latestJobFuture.cancel(true);
    }

    public void cancelHistoricExchangeJob() {
        historicalJobFuture.cancel(true);
    }

    public void reactivateSchedulerConfig() {
        if(null!=this.taskRegistrar) {
            log.info("!!!Destroying task registrar, creating executor service anew!!!");
            this.taskRegistrar.destroy();
            configureTasks(this.taskRegistrar);
        }
    }
}
