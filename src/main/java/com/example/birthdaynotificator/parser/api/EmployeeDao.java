package com.example.birthdaynotificator.parser.api;

import com.example.birthdaynotificator.parser.model.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getEmployees();
}
