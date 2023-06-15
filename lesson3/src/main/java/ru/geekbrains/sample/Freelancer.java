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
        return String.format("%s %s; ���������; �������������� ���������� �����: %.2f (���.); ��������� ������: %.2f (���.)",
                surname, name, calculateSalary(), salary);
    }
}
