package com.example.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.*;

@SpringBootApplication
public class StockProducerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(StockProducerApplication.class, args);
    }

    @Bean
    public CommandLineRunner producerRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
            System.out.println(">>> Producer started...");  // Add this line for confirmation
            Random random = new Random();
            List<String> symbols = List.of("AAPL", "GOOGL", "AMZN", "TSLA");
            while (true) {
                for (String symbol : symbols) {
                    double price = 100 + random.nextDouble() * 1000; // Simulate price
                    String message = String.format("{\"symbol\":\"%s\", \"price\":%.2f, \"timestamp\":%d}", symbol, price, System.currentTimeMillis());
                    System.out.println("Sent: " + message);  // Log each message sent
                    kafkaTemplate.send("stock-prices", message);
                }
                Thread.sleep(1000);
            }
        };
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }
}
