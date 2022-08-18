package com.example.birthdaynotificator.parser.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {

    @CsvBindByPosition(position = 0)
    private String fio;
    @CsvDate("dd.MM.yyyy")
    @CsvBindByPosition(position = 1)
    private LocalDate birthDate;
}
