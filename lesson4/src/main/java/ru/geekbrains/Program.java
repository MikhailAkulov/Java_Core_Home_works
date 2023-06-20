package ru.geekbrains;

public class Program {
    public static void main(String[] args) {
        String[][] array = {
                {"11", "12", "13", "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "33", "34"},
                {"41", "42", "43", "44"}
        };
        try {
            System.out.printf("Сумма элементов массива = %d\n", sumOfArray(array));
        }
        catch (MyArraySizeException e) {
            System.out.printf("%s\nТребуемый размер: 4 х 4", e.getMessage());
        }
        catch (MyArrayDataException e) {
            System.out.printf("%s [%d, %d]\nОшибка преобразования элемента массива в целое число",
                    e.getMessage(), e.getRow() + 1, e.getColumn() + 1);
        }
    }

    public static int sumOfArray(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length != 4 || array[0].length != 4 || array[1].length != 4 ||
                array[2].length != 4 || array[3].length != 4) {
            throw new MyArraySizeException("Неверный размер массива");
        }
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                try{
                    sum += Integer.parseInt(array[i][j]);
                }
                catch (NumberFormatException e) {
                    throw new MyArrayDataException("Неверный формат данных в ячейке", i, j);
                }
            }
        }
        return sum;
    }
}
