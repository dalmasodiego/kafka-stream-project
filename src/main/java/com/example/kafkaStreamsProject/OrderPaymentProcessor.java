package com.example.kafkaStreamsProject;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class OrderPaymentProcessor {

    public static void main(String[] args) {
        // Configurações básicas
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Criação do StreamsBuilder
        StreamsBuilder builder = new StreamsBuilder();

        // Consome mensagens do tópico "input-topic"
        KStream<String, String> stream = builder.stream("input-topic");
        stream.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        // Processa as mensagens (neste caso, converte valores para maiúsculas)
        KStream<String, String> processedStream = stream.mapValues(value -> value.toUpperCase());

        // Envia as mensagens processadas para o tópico "output-topic"
        processedStream.to("output-topic");

        // Cria e inicia o Kafka Streams
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Adiciona shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
