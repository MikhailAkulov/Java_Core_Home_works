package ru.geekbrains;

import java.util.Random;

import ru.geekbrains.sample.*;

public class Program {
    static BaseEmployee generateEmployee(){

        String[] names = new String[] { "Сергей", "Ярослав", "Максим", "Алексей", "Александр", "Владимир", "Кирилл", "Иван", "Пётр", "Денис" };
        String[] surnames = new String[] { "Смирнов", "Иванов", "Петров", "Койд", "Чмелёв", "Александров", "Самсонов", "Гринько", "Чалов", "Лепский" };
        Random random = new Random();
        int typeIndex = random.nextInt(2);
        int salary = random.nextInt(1001 - 500) + 200;
        int salaryIndex = random.nextInt(220 - 100) + 100;
        switch (typeIndex)
        {
            case 0:
                return new Freelancer(names[random.nextInt(10)], surnames[random.nextInt(10)], salary);
            case 1:
                return new Worker(names[random.nextInt(10)], surnames[random.nextInt(10)], salary * salaryIndex);
        }
        return  null;
    }

    public static void main(String[] args) {

        BaseEmployee[] employees = new BaseEmployee[10];
        for (int i = 0; i < employees.length; i++){
            employees[i] = generateEmployee();
        }

        for (BaseEmployee employee : employees) {
            System.out.println(employee);
        }
    }
}
