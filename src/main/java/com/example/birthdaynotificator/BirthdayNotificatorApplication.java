package com.example.birthdaynotificator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BirthdayNotificatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirthdayNotificatorApplication.class, args);
    }

}
