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
        return String.format("%s %s; �������; �������������� ���������� ����� (������������� �������� ������): %.2f (���.)",
                surname, name, salary);
    }
}
