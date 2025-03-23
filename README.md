# Stock Kafka Dashboard

This project is a **real-time stock price processing system** using **Apache Kafka and Spring Boot**. It consists of a **Producer** that generates stock price data, a **Consumer** that processes and analyzes it, and a **REST API** to fetch moving averages.

## 📌 Architecture

```
 +-------------+       +-------------------+        +-------------+       +------------------+
 | StockProducer| ---> | Kafka (stock-prices)| ---> | StockConsumer| ---> | AnalyticsService |
 +-------------+       +-------------------+        +-------------+       +------------------+
                                                           |
                                                           v
                                                 +--------------------+
                                                 | REST API (Spring)  |
                                                 +--------------------+
```

- **Producer**: Generates stock prices and publishes them to a Kafka topic (`stock-prices`).
- **Kafka Broker**: Handles message streaming.
- **Consumer**: Listens to the topic, processes stock prices, and calculates moving averages.
- **AnalyticsService**: Maintains a price window (last 5 prices) and computes the moving average.
- **REST API**: Exposes an endpoint to fetch the moving average for a given stock.

---

## 🚀 Getting Started

### 1️⃣ Prerequisites
Ensure you have the following installed:
- **Java 17+**
- **Maven**
- **Apache Kafka** (running on `localhost:9092`)
- **Zookeeper** (if required by Kafka setup)

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/your-repo/stock-kafka-dashboard.git
cd stock-kafka-dashboard
```

### 3️⃣ Start Kafka (If Not Running)
```sh
docker-compose up -d
```

### 4️⃣ Build and Run the Project
```sh
mvn clean install

# Start Producer/API:
cd producer
mvn spring-boot:run

# Start Consumer/API:
cd consumer
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"  # note a different port num is needed
#
```

---

## 📡 API Usage
Once the application is running, you can fetch the **moving average** of a stock using:

```sh
 curl http://localhost:8081/api/stocks/{symbol}/moving-average
```

**Example:**
```sh
curl http://localhost:8081/api/stocks/GOOGL/moving-average
```
**Response:**
```json
{
    "movingAverage": 854.67
}
```

---

## 🛠 Project Structure
```
stock-kafka-dashboard/
│-- src/main/java/com/example
│   │-- producer/
│   │   ├── StockProducerApplication.java  # Kafka Producer
│   │-- consumer/
│   │   ├── StockConsumer.java  # Kafka Consumer
│   │   ├── AnalyticsService.java  # Business Logic
│   |   │-- controller/
│   │           ├── StockController.java  # REST API
│   ├── StockKafkaDashboardApplication.java  # Main App
│-- pom.xml  # Dependencies
│-- README.md  # This File
```

---

## 🐞 Troubleshooting
### 1️⃣ No messages consumed?
- Ensure **Kafka is running** on `localhost:9092`.
- Check if the topic exists:
  ```sh
  bin/kafka-topics.sh --list --bootstrap-server localhost:9092
  ```
- Reset consumer group offset (if needed):
  ```sh
  bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group stock-group --reset-offsets --to-earliest --execute
  ```

### 2️⃣ Jackson `NoSuchMethodError`
If you see `getNumberTypeFP()` error, ensure all Jackson dependencies are aligned:
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.0</version>
</dependency>
```
Then rebuild:
```sh
mvn clean install
```

---

## 📜 License
MIT License © 2025 Saurav Sahu

---

## ⭐ Contributing
Feel free to open issues or submit PRs!

