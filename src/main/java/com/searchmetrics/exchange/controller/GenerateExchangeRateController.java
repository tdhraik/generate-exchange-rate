package com.searchmetrics.exchange.controller;

import com.searchmetrics.exchange.interactor.GenerateExchangeRateInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("generate/exchange")
public class GenerateExchangeRateController {

    @Autowired
    private GenerateExchangeRateInteractor interactor;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/latest")
    public String getLatestExchangeRate() {
        return interactor.getLatestExchangeRate();
    }
}
