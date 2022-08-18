package com.example.birthdaynotificator.manager.impl;

import com.example.birthdaynotificator.bot.TelegramBot;
import com.example.birthdaynotificator.configuration.TelegramBotConfiguration;
import com.example.birthdaynotificator.manager.BirthdayManager;
import com.example.birthdaynotificator.parser.api.EmployeeDao;
import com.example.birthdaynotificator.parser.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayManagerImpl implements BirthdayManager {

    private static final int DAYS_OF_WEEK = 7;
    private static final String MESSAGE_PATTERN = "%s, %s!";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM");
    private final TelegramBot bot;
    private final EmployeeDao employeeDao;
    private final TelegramBotConfiguration configuration;

    @Scheduled(cron = "*/15 * * ? * *")
    public void notifyUsers() {
        log.info("notify users start!");
        final LocalDate now = LocalDate.now();
        final List<LocalDate> week = Stream.iterate(now, d -> d.plusDays(1))
                .limit(DAYS_OF_WEEK)
                .collect(Collectors.toList());
        final String message = employeeDao.getEmployees().stream()
                .filter(e -> isBirthOnWeek(week, e))
                .map(e -> String.format(MESSAGE_PATTERN, e.getFio(), DATE_TIME_FORMATTER.format(e.getBirthDate())))
                .collect(Collectors.joining("\n"));
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Это канал о самых вкусных пирожочках\nКогда-то свой Др отмечает:\n" + message);
        sendMessage.setChatId(configuration.getChatId());
        log.info("employees: {}", message);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Something went wrong", e);
        }
    }

    private boolean isBirthOnWeek(List<LocalDate> week, Employee employee) {
        return week.stream()
                .filter(d -> d.getMonth() == employee.getBirthDate().getMonth())
                .anyMatch(d -> d.getDayOfMonth() == employee.getBirthDate().getDayOfMonth());
    }
}
