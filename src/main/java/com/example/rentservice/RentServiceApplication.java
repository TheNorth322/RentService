package com.example.rentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentServiceApplication {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "E:/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "lfybctvj3228");
        SpringApplication.run(RentServiceApplication.class, args);
    }

}
