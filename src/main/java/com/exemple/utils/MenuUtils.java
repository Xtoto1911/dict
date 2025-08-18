package com.exemple.utils;

import com.exemple.dictionary.BaseDictionary;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuUtils {

    public static final List<String> MAIN_MENU = List.of(
            "1. Латинский словарь",
            "2. Словарь с цифрами",
            "3. Показать содержимое словарей",
            "4. Выход"
    );

    public static final List<String> DICTIONARY_MENU = List.of(
            "1. Найти по ключу",
            "2. Добавить слово",
            "3. Удалить слово",
            "4. Вывод словаря",
            "5. Поменять файл",
            "6. Назад"
    );

    public static String readInput(Scanner scanner) {
        return scanner.nextLine();
    }

    public static boolean checkFile(File file) {
        return file.exists() && file.isFile();
    }

    public static File createFile(File file) {
        try{
            if(!checkFile(file.getAbsoluteFile())) {
                file.createNewFile();
                return file;
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createFile(String path) {
        File file = new File(path);
        return createFile(file);
    }

    public static void showMessage(String msg) {
        System.out.println(msg);
    }

    public static void showMenu(List<String> list) {
        for (String str : list) {
            showMessage(str);
        }
    }

    public static void showDictionaries(BaseDictionary... dictionaries) {
        for(BaseDictionary dictionary : dictionaries) {
            String strDict = StringUtils.isNotEmpty(dictionary.toString()) ? dictionary.toString() : "Спипок пуст";
            System.out.println("===============");
            System.out.println(strDict);
            System.out.println("===============");
        }
    }

}
