package com.example.kafkaStreamsProject.objects;

import java.io.Serializable;

public class Transaction implements Serializable {

    private String transactionId;
    private String accountId;
    private double amount;
    private String location;

    // Construtor padrão
    public Transaction() {
    }

    // Construtor com todos os campos
    public Transaction(String transactionId, String accountId, double amount, String location) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.location = location;
    }

    // Getters e Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    // Método para exibir informações da transação
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", location='" + location + '\'' +
                '}';
    }
}
