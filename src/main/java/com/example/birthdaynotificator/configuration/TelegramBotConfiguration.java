package com.example.birthdaynotificator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "birthday-notification")
public class TelegramBotConfiguration {

    private String username;
    private String token;
    private String chatId;
    private String path;

}
