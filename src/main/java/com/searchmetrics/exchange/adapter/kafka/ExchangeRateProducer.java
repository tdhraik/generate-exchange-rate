package com.searchmetrics.exchange.adapter.kafka;

import com.searchmetrics.exchange.adapter.coinlayer.ExchangeRateResponse;
import com.searchmetrics.exchange.dto.EnrichedExchangeRateMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateProducer {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateProducer.class);

    @Value("${spring.kafka.exchange.rate.topic.latest}")
    private String exchangeRateTopicName;

    @Value("${spring.kafka.exchange.rate.topic.historic}")
    private String historicRateTopicName;

    private KafkaTemplate<String, String> kafkaTemplate;

    public ExchangeRateProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLatestExchangeRateMsg(ExchangeRateResponse message, String fromCurrency, String toCurrency) {
        this.kafkaTemplate.send(exchangeRateTopicName,
                EnrichedExchangeRateMsg.toEnrichedExchangeRateMsg(message, fromCurrency, toCurrency));
        log.info("Message sent to topic {} - {}", exchangeRateTopicName, message);
    }

    public void sendHistoricExchangeRateMsg(ExchangeRateResponse message, String fromCurrency, String toCurrency) {
        this.kafkaTemplate.send(historicRateTopicName,
                EnrichedExchangeRateMsg.toEnrichedExchangeRateMsg(message, fromCurrency, toCurrency));
        log.info("Message sent to topic {} - {}", historicRateTopicName, message);
    }
}
