package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Tic_tac_toe {

    private static int WIN_COUNT = 4;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '•';

    private static final Scanner SCANNER = new Scanner(System.in);

    private static char[][] field;  // Двумерный массив хранит текущее состояние игрового поля

    private static final Random random = new Random();

    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля


    public static void main(String[] args) {
        do {
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да) >>> ");
        } while (SCANNER.next().equalsIgnoreCase("Y"));
        System.out.print("Спасибо за игру");
    }


    /**
     * Инициализация игрового поля
     */
    private static void initialize(){
        // Задаем размерность игрового поля
        int x, y;
        do {
            System.out.print("Задайте размер игрового поля не менее 3х3 через пробел >>> ");
            x = SCANNER.nextInt();
            y = SCANNER.nextInt();
        } while (x < 3 || y < 3);

        fieldSizeX = x;
        fieldSizeY = y;

        // Задаем выигрышную комбинацию
        if (x >= 3 && y >= 3) {
            int winComb;
            do{
                System.out.print("Задайте количество последовательно расположенных символов для победы (не менее 3) >>> ");
                winComb = SCANNER.nextInt();
            } while (winComb <= 2);
            WIN_COUNT = winComb;
        }

        field = new char[fieldSizeX][fieldSizeY];
        // Пройдем по всем элементам массива
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                // Проинициализируем все элементы массива DOT_EMPTY (признак пустого поля)
                field[j][i] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     */
    private static void printField(){

        // Header
        System.out.print("0");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++){
            System.out.print((i % 2 == 0) ? "|" : i / 2 + 1);
        }
        System.out.println();

        // Body
        for (int i = 0; i < fieldSizeY; i++){
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++) {
                System.out.print(field[j][i] + "|");
            }
            System.out.println();
        }

        // Footer
        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("=");
        }
        System.out.println();
    }

    /**
     * Обработка хода игрока (человек)
     */
    private static void humanTurn(){
        int x, y;
        do
        {
            System.out.print("Введите координаты хода X и Y через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, ячейка является пустой
     * @param x координата X
     * @param y координата Y
     * @return true or false
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности ввода
     * (координаты хода не должны превышать размерность массива, игрового поля)
     * @param x координата X
     * @param y координата Y
     * @return true or false
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 &&  x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход компьютера
     */
    private static void aiTurn(){
        int x = -1;
        int y = -1;
        // Компьютер пытается блокировать ходы человека
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                // Проверка строки вправо
                if (checkRightLine(i, j, DOT_HUMAN) == WIN_COUNT-2) {
                    if (isCellValid(i + WIN_COUNT-2, j) && isCellEmpty(i + WIN_COUNT-2, j)) {
                        x = i + WIN_COUNT-2;
                        y = j;
                    } else if (isCellValid(i - 1, j) && isCellEmpty(i - 1, j)) {
                        x = i - 1;
                        y = j;
                    }
                }
                // Проверка диагонали вправо вверх
                else if (checkRightUpDiagonal(i, j, DOT_HUMAN) == WIN_COUNT-2) {
                    if (isCellValid(i + WIN_COUNT-2, j - WIN_COUNT-2) && isCellEmpty(i + WIN_COUNT-2, j - WIN_COUNT-2)) {
                        x = i + WIN_COUNT-2;
                        y = j - WIN_COUNT-2;
                    } else if (isCellValid(i - 1, j + 1) && isCellEmpty(i - 1, j + 1)) {
                        x = i - 1;
                        y = j + 1;
                    }
                }
                // Проверка диагонали вправо вниз
                else if (checkRightDownDiagonal(i, j, DOT_HUMAN) == WIN_COUNT-2) {
                    if (isCellValid(i + WIN_COUNT-2, j + WIN_COUNT-2) && isCellEmpty(i + WIN_COUNT-2, j + WIN_COUNT-2)) {
                        x = i + WIN_COUNT-2;
                        y = j + WIN_COUNT-2;
                    } else if (isCellValid(i - 1, j - 1) && isCellEmpty(i - 1, j - 1)) {
                        x = i - 1;
                        y = j - 1;
                    }
                }
                // Проверка ряда вниз
                else if (checkLineDown(i, j, DOT_HUMAN) == WIN_COUNT-2) {
                    if (isCellValid(i, j + WIN_COUNT - 2) && isCellEmpty(i, j + WIN_COUNT - 2)) {
                        x = i;
                        y = j + WIN_COUNT - 2;
                    } else if (isCellValid(i, j - 1) && isCellEmpty(i, j - 1)) {
                        x = i;
                        y = j - 1;
                    }
                }
            }
        }
        if (x == -1 && y == -1) {
            do {
                x = random.nextInt(fieldSizeX);
                y = random.nextInt(fieldSizeY);
            }
            while (!isCellEmpty(x, y));
        }
        field[x][y] = DOT_AI;
    }

    /**
     * Подсчёт последовательно расположенных символов вправо
     * @param x начальная координата X
     * @param y начальная координата Y
     * @param c проверяемый символ человека или компьютера
     * @return количество последовательно расположенных символов
     */
    static int checkRightLine(int x, int y, char c) {
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                x++;
            }
            else return count;
        } while (x < fieldSizeX);
        return count;
    }

    /**
     * Подсчёт последовательно расположенных символов вниз
     * @param x начальная координата X
     * @param y начальная координата Y
     * @param c проверяемый символ человека или компьютера
     * @return количество последовательно расположенных символов
     */
    static int checkLineDown(int x, int y, char c) {
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                y++;
            }
            else return count;
        } while (y < fieldSizeY);
        return count;
    }

    /**
     * Подсчёт последовательно расположенных символов в диагонали вправо-вверх
     * @param x начальная координата X
     * @param y начальная координата Y
     * @param c проверяемый символ человека или компьютера
     * @return количество последовательно расположенных символов
     */
    static int checkRightUpDiagonal(int x, int y, char c) {
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                x++;
                y--;
            }
            else return count;
        } while (x < fieldSizeX && y >= 0);
        return count;
    }

    /**
     * Подсчёт последовательно расположенных символов в диагонали вправо-вниз
     * @param x начальная координата X
     * @param y начальная координата Y
     * @param c проверяемый символ человека или компьютера
     * @return количество последовательно расположенных символов
     */
    static int checkRightDownDiagonal(int x, int y, char c) {
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                x++;
                y++;
            }
            else return count;
        } while (x < fieldSizeX && y < fieldSizeY);
        return count;
    }

    /**
     * Проверка победы
     * @param c проверяемый символ человека или компьютера
     * @return true or false
     */
    static boolean checkWin(char c){
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkRightLine(i, j, c) >= WIN_COUNT || checkRightUpDiagonal(i, j, c) >= WIN_COUNT 
                        || checkRightDownDiagonal(i, j, c) >= WIN_COUNT || checkLineDown(i, j, c) >= WIN_COUNT) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверка на ничью
     * @return true or false
     */
    static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки состояния игры
     * @param c проверяемый символ человека или компьютера
     * @param str сообщение
     * @return true or false
     */
    static boolean gameCheck(char c, String str){
        if (checkWin(c)){
            System.out.println(str);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }
}
