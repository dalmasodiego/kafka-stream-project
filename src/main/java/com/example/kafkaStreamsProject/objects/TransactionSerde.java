package com.example.kafkaStreamsProject.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TransactionSerde implements Serde<Transaction> {

    private final Serializer<Transaction> serializer;
    private final Deserializer<Transaction> deserializer;

    // Construtor
    public TransactionSerde() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.serializer = new TransactionSerializer(objectMapper);
        this.deserializer = new TransactionDeserializer(objectMapper);
    }

    @Override
    public Serializer<Transaction> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<Transaction> deserializer() {
        return deserializer;
    }

    // Serializador
    public static class TransactionSerializer implements Serializer<Transaction> {
        private final ObjectMapper objectMapper;

        public TransactionSerializer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public byte[] serialize(String topic, Transaction data) {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao serializar Transaction", e);
            }
        }
    }

    // Desserializador
    public static class TransactionDeserializer implements Deserializer<Transaction> {
        private final ObjectMapper objectMapper;

        public TransactionDeserializer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Transaction deserialize(String topic, byte[] data) {
            try {
                return objectMapper.readValue(data, Transaction.class);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao desserializar Transaction", e);
            }
        }
    }
}