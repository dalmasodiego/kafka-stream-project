package com.example.kafkaStreamsProject;

import com.example.kafkaStreamsProject.objects.Transaction;
import com.example.kafkaStreamsProject.objects.TransactionSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Properties;

public class FraudulentTransaction {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "fraudulent-transaction-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TransactionSerde.class.getName());
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);


        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Transaction> transactions = builder.stream("transactions-topic");

        // Transações de alto valor
        KStream<String, Transaction> highValueTransactions = transactions.filter(
                (key, transaction) -> transaction.getAmount() > 10000
        );

        // Contagem de transações frequentes
        KTable<Windowed<String>, Long> frequentTransactions = transactions
                .groupBy((key, transaction) -> transaction.getAccountId(), Grouped.with(Serdes.String(), new TransactionSerde()))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
                .count();


        frequentTransactions.toStream().to("frequent-transactions-topic", Produced.with(
                WindowedSerdes.timeWindowedSerdeFrom(String.class),
                Serdes.Long()
        ));

        highValueTransactions.to("suspicious-transactions-topic", Produced.with(Serdes.String(), new TransactionSerde()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

}


