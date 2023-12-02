package com.example.rentservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentFrequency {
    MONTHLY("monthly"),
    QUARTERLY("quarterly");

    private final String value;

    PaymentFrequency(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
