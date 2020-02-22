package com.searchmetrics.exchange.adapter.kafka;

import com.searchmetrics.exchange.adapter.coinlayer.ExchangeRateResponse;
import com.searchmetrics.exchange.dto.EnrichedExchangeRateMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateProducer {

    @Value("${spring.kafka.exchange.rate.topic.latest}")
    private String exhangeRateTopicName;

    @Value("${spring.kafka.exchange.rate.topic.historic}")
    private String historicRateTopicName;

    private KafkaTemplate<String, String> kafkaTemplate;

    public ExchangeRateProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLatestExchangeRateMsg(ExchangeRateResponse message, String fromCurrency, String toCurrency) {
        this.kafkaTemplate.send(exhangeRateTopicName,
                EnrichedExchangeRateMsg.toEnrichedExchangeRateMsg(message, fromCurrency, toCurrency));
        System.out.println(String.format("Message sent to topic {%s} - {%s}", exhangeRateTopicName, message));
    }

    public void sendHistoricExchangeRateMsg(ExchangeRateResponse message, String fromCurrency, String toCurrency) {
        this.kafkaTemplate.send(historicRateTopicName,
                EnrichedExchangeRateMsg.toEnrichedExchangeRateMsg(message, fromCurrency, toCurrency));
        System.out.println(String.format("Message sent to topic {%s} - {%s}", historicRateTopicName, message));
    }
}
