package com.exemple.menu;

import com.exemple.dictionary.BaseDictionary;
import com.exemple.dictionary.LatinDictionary;
import com.exemple.dictionary.NumDictionary;
import com.exemple.utils.MenuUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class DictionaryMenu {
    private final Scanner scanner;
    private final Map<BaseDictionary, File> dictionaries;

    private final BaseDictionary latinDictionary;
    private final BaseDictionary numDictionary;

    public DictionaryMenu(LatinDictionary latinDictionary, NumDictionary numDictionary) {
        this.latinDictionary = latinDictionary;
        this.numDictionary = numDictionary;
        scanner = new Scanner(System.in);
        dictionaries = new LinkedHashMap<>();
        initDictionaries();
    }

    private void initDictionaries() {
        addDictionary(latinDictionary, MenuUtils.createFile("latin.txt"));
        addDictionary(numDictionary, MenuUtils.createFile("num.txt"));
    }

    private void addDictionary(BaseDictionary dictionary, File file) {
        if (MenuUtils.checkFile(file)) {
            file = MenuUtils.createFile(file);
        }
        dictionaries.put(dictionary, file);
    }

    private BaseDictionary getDictionaryByClass(Class<? extends BaseDictionary> cl) {
        for (BaseDictionary dictionary : dictionaries.keySet()) {
            if (cl.isInstance(dictionary)) {
                return dictionary;
            }
        }
        return null;
    }


    private void fillDictionaries() {
        for (Map.Entry<BaseDictionary, File> entry : dictionaries.entrySet()) {
            try {
                entry.getKey().fillFromFile(entry.getValue().getAbsolutePath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Ошибка формата в " + entry.getValue().getName() + ". Словарь будет пустым");
                entry.getKey().getDictionary().clear();
            }
        }
    }

    private void dictionaryMenu(BaseDictionary dictionary, String path) {
        boolean isExit = false;
        while (!isExit) {
            try {
                MenuUtils.showMenu(MenuUtils.DICTIONARY_MENU);
                switch (MenuUtils.readInput(scanner)) {
                    case "1" -> handleFindByKey(dictionary);
                    case "2" -> handleAdd(dictionary);
                    case "3" -> handleRemove(dictionary);
                    case "4" -> MenuUtils.showDictionaries(dictionary);
                    case "5" -> path = handleNewFile(dictionary);
                    case "6" -> isExit = true;
                    default -> MenuUtils.showMessage("Нет такого действия");
                }
                dictionary.saveToFile(path);
            } catch (IOException | IllegalArgumentException e) {
                MenuUtils.showMessage(e.getMessage());
            }
        }
    }

    private String handleNewFile(BaseDictionary dictionary) throws IOException {
        MenuUtils.showMessage("Введите путь к файлу: ");
        String path = MenuUtils.readInput(scanner);
        File newFile = new File(path);
        if (!MenuUtils.checkFile(newFile)) {
            MenuUtils.createFile(newFile);
        }
        dictionary.getDictionary().clear();
        dictionary.fillFromFile(newFile.getAbsolutePath());
        dictionaries.put(dictionary, newFile);
        return newFile.getAbsolutePath();
    }

    private void handleRemove(BaseDictionary dictionary) {
        MenuUtils.showMessage("Удалить слово: ");
        dictionary.remove(MenuUtils.readInput(scanner));
    }

    private void handleFindByKey(BaseDictionary dictionary) throws IllegalArgumentException {
        MenuUtils.showMessage("Найти слово:");
        String key = MenuUtils.readInput(scanner);
        String value = dictionary.find(key);
        MenuUtils.showMessage(key + dictionary.getSeparator() + value);
    }

    private void handleAdd(BaseDictionary dictionary) {
        MenuUtils.showMessage("Добавить слово:");
        String key = MenuUtils.readInput(scanner);
        MenuUtils.showMessage("Перевод слова:");
        String value = MenuUtils.readInput(scanner);
        dictionary.add(key, value);
    }

    public void run() {
        boolean isExit = false;
        BaseDictionary latin = getDictionaryByClass(LatinDictionary.class);
        BaseDictionary num = getDictionaryByClass(NumDictionary.class);
        fillDictionaries();
        while (!isExit) {
            MenuUtils.showMenu(MenuUtils.MAIN_MENU);
            switch (MenuUtils.readInput(scanner)) {
                case "1" -> dictionaryMenu(latin, dictionaries.get(latin).getAbsolutePath());
                case "2" -> dictionaryMenu(num, dictionaries.get(num).getAbsolutePath());
                case "3" -> MenuUtils.showDictionaries(latin, num);
                case "4" -> {
                    isExit = true;
                    scanner.close();
                }
                default -> MenuUtils.showMessage("Нет такого действия");
            }
        }
    }


}
