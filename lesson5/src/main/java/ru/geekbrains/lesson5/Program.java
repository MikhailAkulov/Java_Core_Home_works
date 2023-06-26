package ru.geekbrains.lesson5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {

    /**
     1.  Создать 2 текстовых файла, примерно по 50-100 символов в каждом(особого значения не имеет);
     2.  Написать метод, «склеивающий» эти файлы, то есть вначале идет текст из первого файла, потом текст из второго.
     3.* Написать метод, который проверяет, присутствует ли указанное пользователем слово в файле (работаем только с латиницей).
     4.* Написать метод, проверяющий, есть ли указанное слово в папке
     */

    private static final Random random = new Random();
    private static final int CHAR_BOUND_L = 65; // Номер начального символа
    private static final int CHAR_BOUND_H = 90; // Номер конечного символа
    private static final String SEARCH_WORD = "GeekBrains"; // для поиска

    public static void main(String[] args) throws IOException {
//        String str = generateSymbols(15);
//        System.out.println(str);
        writeFileContents("sample01.txt", 30, 5);
        System.out.println(searchInFile("sample01.txt", SEARCH_WORD));

        writeFileContents("sample02.txt", 30, 5);
        System.out.println(searchInFile("sample02.txt", SEARCH_WORD));
        concatenate("sample01.txt", "sample02.txt", "sample01_out.txt");
        System.out.println(searchInFile("sample01_out.txt", SEARCH_WORD));

        Tree.print(new File("."), "", true);

        String[] fileNames = new String[10];
        for (int i = 0; i < fileNames.length; i++){
            fileNames[i] = "file_" + i + ".txt";
            writeFileContents(fileNames[i], 100, 4);
            System.out.printf("Файл %s создан.\n", fileNames[i]);
        }

        List<String> result = searchMatch(fileNames, SEARCH_WORD);
        for (String s: result) {
            System.out.printf("Файл %s содержит искомое слово '%s'\n", s, SEARCH_WORD);
        }
        backup(".");
    }

    /**
     * Метод генерации случайной последовательности символов
     * @param amount количество символов
     * @return последовательность символов
     */
    private static String generateSymbols(int amount){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++)
            stringBuilder.append((char)random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H + 1));
        return stringBuilder.toString();
    }

    /**
     * Записать последовательность символов в файл
     * @param fileName имя файла
     * @param length длина последовательности символов
     * @throws IOException
     */
    private static void writeFileContents(String fileName, int length) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)){
            fileOutputStream.write(generateSymbols(length).getBytes());
        }
//        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//        fileOutputStream.write(generateSymbols(length).getBytes());
//        fileOutputStream.flush();
//        fileOutputStream.close();
    }

    /**
     * Запись последовательности символов в файл со случайной вставкой искомого слова
     * @param fileName имя файла
     * @param length длина последовательности символов
     * @param words количество слов для поиска
     * @throws IOException
     */
    private static void writeFileContents(String fileName, int length, int words) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)){
            for (int i = 0; i < words; i++){
                if (random.nextInt(5) == 5 / 2){
                    fileOutputStream.write(SEARCH_WORD.getBytes());
                }
                else {
                    fileOutputStream.write(generateSymbols(length).getBytes());
                }
            }
            fileOutputStream.write(' ');
        }
    }

    /**
     * Склейка двух файлов в один новый
     * @param fileIn1 имя первого файла
     * @param fileIn2 имя второго файла
     * @param fileOut имя результирующего файла
     * @throws IOException
     */
    private static void concatenate(String fileIn1, String fileIn2, String fileOut) throws IOException {
        // На запись
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileOut)){
            int c;
            // На чтение
            try(FileInputStream fileInputStream = new FileInputStream(fileIn1)){
                while ( (c = fileInputStream.read()) != -1)
                    fileOutputStream.write(c);
            }
            // На чтение
            try(FileInputStream fileInputStream = new FileInputStream(fileIn2)){
                while ( (c = fileInputStream.read()) != -1)
                    fileOutputStream.write(c);
            }
        }
    }

    /**
     * Проверка наличия искомого слова в файлах
     * @param fileName имя файла
     * @param search строка для поиска
     * @return результат поиска
     */
    private static boolean searchInFile(String fileName, String search) throws IOException{
        try(FileInputStream fileInputStream = new FileInputStream(fileName)){
            byte[] searchData = search.getBytes();
            int c;
            int i = 0;
            while ((c = fileInputStream.read()) != - 1){
                if (c == searchData[i]){
                    i++;
                }
                else {
                    i = 0;
                    if (c == searchData[i]) // GeekBrainGeekBrains
                        i++;
                    continue;
                }
                if (i == searchData.length){
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Проверка наличия искомого слова в файлах текущей директории
     * @param files массив имён файлов
     * @param search строка для поиска
     * @return список файлов, содержащих искомое слово
     * @throws IOException
     */
    private static List<String> searchMatch(String[] files, String search) throws IOException {
        List<String> list = new ArrayList<>();
        File path = new File(new File(".").getCanonicalPath());
        File[] dir = path.listFiles();
        for (int i = 0; i < dir.length; i++){
            if (dir[i].isDirectory())
                continue;
            for (int j = 0; j < files.length; j++){
                if (dir[i].getName().equals(files[j])){
                    if (searchInFile(dir[i].getName(), search)){
                        list.add(dir[i].getName());
                        break;
                    }
                }
            }
        }
        return list;
    }

    /**
     * Копирование файлов из директории во вновь созданную папку ./backup
     * @param directory имя директории для копирования
     * @throws IOException
     */
    private static void backup(String directory) throws IOException {
        File backupDir = new File(directory + "/backup");
        boolean createBackup = backupDir.mkdir();
        File path = new File(new File(directory).getCanonicalPath());
        File[] dir = path.listFiles();
        assert dir != null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        for (File file : dir) {
            if (file.isDirectory())
                continue;
            try {
                inputStream = new FileInputStream(file);
                File backupFile = new File(backupDir.getPath() + "\\" + file.getName());
                outputStream = new FileOutputStream(backupFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            finally {
                assert inputStream != null;
                inputStream.close();
                assert outputStream != null;
                outputStream.close();
            }
        }
    }
}
