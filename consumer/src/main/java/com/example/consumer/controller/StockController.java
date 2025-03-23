package com.example.consumer.controller;


import com.example.consumer.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final AnalyticsService analyticsService;


    public StockController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/{symbol}/moving-average")
    public ResponseEntity<Map<String, Double>> getMovingAverage(@PathVariable String symbol) {
        double avg = analyticsService.getMovingAverage(symbol.toUpperCase());
        return ResponseEntity.ok(Collections.singletonMap("movingAverage", avg));
    }
}
