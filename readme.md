# KafkaStreamProject

## Introduction
Project aimed at learning purposes.

## Purpose
The purpose of this project is to simulate a stream using Kafka

### Classes to Run
1. **FraudulentTransaction**
2. **OrderPaymentProcessor**

## Configuration
1. Run the [docker-compose.yml](docker-compose.yml) file to install Kafka containers.
2. The version of the Docker container images is specified in the [.env](.env) file.

## Kafka Topics Creation

### Steps:

1. **Start Containers**:
    ```bash
    docker compose up -d
    ```

2. **Create Topics**:
    ```bash
    # input-topic
    docker exec -it kafka kafka-topics --create --topic input-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

    # output-topic
    docker exec -it kafka kafka-topics --create --topic output-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

    # transactions-topic
    docker exec -it kafka kafka-topics --create --topic transactions-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

    # suspicious-transactions-topic
    docker exec -it kafka kafka-topics --create --topic suspicious-transactions-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
    ```

3. **Produce Messages to a Topic**:
    ```bash
    docker exec -it kafka kafka-console-producer --broker-list localhost:9092 --topic input-topic --property parse.key=true --property key.separator=:
    ```

4. **Consume Messages from a Topic**:
    ```bash
    docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic output-topic --from-beginning
    ```
