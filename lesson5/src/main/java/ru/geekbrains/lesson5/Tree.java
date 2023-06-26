package ru.geekbrains.lesson5;

import java.io.File;

public class Tree {

    /**
     * Печать дерева в консоль
     * @param file имя директории / файла
     * @param indent отступ для первой директории
     * @param isLast флаг - является ли директория / файл последним
     */
    public static void print(File file, String indent, boolean isLast){
        System.out.print(indent); // Рисуем отступ
        if (isLast){
            System.out.print("└─");
            indent += "  ";
        }
        else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if (files == null)
            return;

        int subDirTotal = 0;
        int filesTotal = 0;
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory()) {
                subDirTotal++;
            } else if (files[i].isFile()) {
                filesTotal++;
            }
        }

        int subDirCounter = 0;
        int filesCounter = 0;
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory()){
                print(files[i], indent, subDirCounter  == subDirTotal - 1);
                subDirCounter++;
            } else if (files[i].isFile()) {
                print(files[i], indent, filesCounter == filesTotal - 1);
                filesCounter++;
            }
        }
    }
}
