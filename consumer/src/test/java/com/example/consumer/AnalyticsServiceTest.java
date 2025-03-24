package com.example.consumer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnalyticsServiceTest {
    @Test
    public void testMovingAvgSinglePrice() {
        AnalyticsService analyticsService = new AnalyticsService();
        analyticsService.updatePrice("MSFT", 150.0, System.currentTimeMillis());
        analyticsService.updatePrice("AAPL", 100.0, System.currentTimeMillis());
        analyticsService.updatePrice("MSFT", 250.0, System.currentTimeMillis());
        analyticsService.updatePrice("AAPL", 200.0, System.currentTimeMillis());
        analyticsService.updatePrice("AAPL", 300.0, System.currentTimeMillis());

        assertEquals(200.0, analyticsService.getMovingAverage("MSFT"), 0.001);
        assertEquals(200.0, analyticsService.getMovingAverage("AAPL"), 0.001);
    }

    @Test
    public void testMovingAverageWindowLimit() {
        AnalyticsService service = new AnalyticsService();
        // Add more than WINDOW_SIZE (5) prices
        service.updatePrice("GOOGL", 10, System.currentTimeMillis());
        service.updatePrice("GOOGL", 20, System.currentTimeMillis());
        service.updatePrice("GOOGL", 30, System.currentTimeMillis());
        service.updatePrice("GOOGL", 40, System.currentTimeMillis());
        service.updatePrice("GOOGL", 50, System.currentTimeMillis());
        service.updatePrice("GOOGL", 60, System.currentTimeMillis()); // This should push out 10
        double avg = service.getMovingAverage("GOOGL");
        // Expected average: (20+30+40+50+60)/5 = 40
        assertEquals(40.0, avg, 0.001);
    }

}
