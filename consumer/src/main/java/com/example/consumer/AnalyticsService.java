package com.example.consumer;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnalyticsService {
    private final Map<String, Deque<Double>> priceWindows = new ConcurrentHashMap<>();
    private final Map<String, Double> movingAverages = new ConcurrentHashMap<>();
    private static final int WINDOW_SIZE = 5;

    public void updatePrice(String symbol, double price, long timestamp) {
        priceWindows.computeIfAbsent(symbol, k -> new ArrayDeque<>()).add(price);
        Deque<Double> window = priceWindows.get(symbol);

        if (window.size() > WINDOW_SIZE) {
            window.poll();
        }
        double avg = window.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        movingAverages.put(symbol, avg);
    }

    public double getMovingAverage(String symbol) {
        return movingAverages.getOrDefault(symbol, 0.0);
    }
}
