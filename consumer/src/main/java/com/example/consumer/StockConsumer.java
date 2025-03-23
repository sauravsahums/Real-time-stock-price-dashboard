package com.example.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer
{
    private final AnalyticsService analyticsService;

    public StockConsumer(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @KafkaListener(topics = "stock-prices", groupId = "stock-group")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(message); // enforces checked exception
        String symbol = node.get("symbol").asText();
        double price = node.get("price").asDouble();
        long timestamp = node.get("timestamp").asLong();
        System.out.println("Consumed msg: " + symbol + " @price " + price);
        analyticsService.updatePrice(symbol, price, timestamp);
    }
}
