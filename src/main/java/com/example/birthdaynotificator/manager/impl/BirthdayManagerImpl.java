package com.example.birthdaynotificator.manager.impl;

import com.example.birthdaynotificator.bot.TelegramBot;
import com.example.birthdaynotificator.configuration.TelegramBotConfiguration;
import com.example.birthdaynotificator.manager.BirthdayManager;
import com.example.birthdaynotificator.parser.api.EmployeeDao;
import com.example.birthdaynotificator.parser.model.Employee;
import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayManagerImpl implements BirthdayManager {

    private final TelegramBot bot;
    private final EmployeeDao employeeDao;
    private final TelegramBotConfiguration configuration;

    @Scheduled(cron = "*/15 * * ? * *")
    public void notifyUsers() {
        final List<Employee> employees = employeeDao.getEmployees();
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Это канал о самых вкусных пирожочках\nКогда-то свой Др отмечает:\n" + employees);
        sendMessage.setChatId(configuration.getChatId());

        log.info("employees: {}", employees);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Something went wrong", e);
        }
    }
}
