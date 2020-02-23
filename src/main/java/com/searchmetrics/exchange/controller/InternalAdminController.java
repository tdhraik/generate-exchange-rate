package com.searchmetrics.exchange.controller;

import com.searchmetrics.exchange.config.SchedulerConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InternalAdminController {

    private SchedulerConfig schedulerConfig;

    public InternalAdminController(SchedulerConfig schedulerConfig) {
        this.schedulerConfig = schedulerConfig;
    }

    @GetMapping("/refresh")
    public void schedulerConfigRefresh() {
        schedulerConfig.cancelLatestExchangeJob();
        schedulerConfig.cancelHistoricExchangeJob();
        schedulerConfig.reactivateSchedulerConfig();
    }
}
