package ru.geekbrains.sample;

public class Worker extends BaseEmployee{

    public Worker(String name, String surname, double salary) {
        super(name, surname, salary);
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Worker; Average monthly salary (fixed monthly payment): %.2f (rub.)",
                surname, name, salary);
    }
}
