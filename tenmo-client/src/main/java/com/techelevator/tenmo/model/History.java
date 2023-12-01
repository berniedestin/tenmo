package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class History {
        @JsonProperty("transaction_id")
        private int transactionId;

        @JsonProperty("transaction_date")
        private LocalDate transactionDate;

        @JsonProperty("from_id")
        private int fromId;

        @JsonProperty("to_id")
        private int toId;

        private BigDecimal amount;
        private String status;

        public History(){}
        public History(int transactionId, LocalDate transactionDate, int fromId, int toId, double amount, String status){

            this.transactionId = transactionId;
            this.transactionDate = transactionDate;
            this.fromId = fromId;
            this.toId = toId;
            this.amount = BigDecimal.valueOf(amount);
            this.status = status;

        }

        public int getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(int transactionId) {
            this.transactionId = transactionId;
        }

        public LocalDate getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate.toLocalDate();
        }
//        public void setTransactionDate(LocalDate transactionDate) {
//            this.transactionDate = transactionDate;
//        }

        public int getFromId() {
            return fromId;
        }

        public void setFromId(int fromId) {
            this.fromId = fromId;
        }

        public int getToId() {
            return toId;
        }

        public void setToId(int toId) {
            this.toId = toId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }


