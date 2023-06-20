package ru.geekbrains;

public class MyArrayDataException extends Exception{
    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public MyArrayDataException(String message, int row, int column) {
        super(message);
        this.row = row;
        this.column = column;
    }
}
