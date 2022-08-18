package com.example.birthdaynotificator.parser;

import com.example.birthdaynotificator.configuration.TelegramBotConfiguration;
import com.example.birthdaynotificator.parser.api.EmployeeDao;
import com.example.birthdaynotificator.parser.model.Employee;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final TelegramBotConfiguration configuration;

    @Override
    public List<Employee> getEmployees() {
        try {
            return new CsvToBeanBuilder<Employee>(new FileReader(configuration.getPath()))
                    .withType(Employee.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            log.error("Can`t parse input file");
            throw new RuntimeException(e);
        }
    }

}
