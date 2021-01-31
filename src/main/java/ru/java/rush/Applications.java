package ru.java.rush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication
public class Applications {

    public static void main(String[] args) {
        SpringApplication.run(Applications.class);
    }
}