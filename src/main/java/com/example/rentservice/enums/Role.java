package com.example.rentservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    INDIVIDUAL("individual"),
    ENTITY("entity"),
    ADMIN("admin");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Role fromDescription(String description) {
        for (Role role : Role.values()) {
            if (role.getDescription().equalsIgnoreCase(description)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + description + "]");
    }
}
