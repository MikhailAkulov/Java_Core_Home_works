package ru.geekbrains.sample;

public class Freelancer extends BaseEmployee{
    public Freelancer(String name, String surname, double salary) {
        super(name, surname, salary);
    }

    @Override
    public double calculateSalary() {
        return 20.8 * 8 * salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Freelancer; Average monthly salary: %.2f (rub.); Hourly rate: %.2f (rub.)",
                surname, name, calculateSalary(), salary);
    }
}
