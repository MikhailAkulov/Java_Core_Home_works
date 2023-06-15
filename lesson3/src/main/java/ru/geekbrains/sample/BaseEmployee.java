package ru.geekbrains.sample;

public abstract class BaseEmployee implements Comparable<BaseEmployee>{
    protected String name;
    protected String surname;
    protected double salary;

    abstract double calculateSalary();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public BaseEmployee(String name, String surname, double salary) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @Override
    public int compareTo(BaseEmployee o) {
        return (int) (this.calculateSalary() - o.calculateSalary());
    }
}
