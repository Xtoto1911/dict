package com.exemple.menu;

import com.exemple.dictionary.BaseDictionary;
import com.exemple.dictionary.LatinDictionary;
import com.exemple.dictionary.NumDictionary;
import com.exemple.utils.MenuUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class DictionaryMenu {
    private final Scanner scanner;
    private final Map<BaseDictionary, File> dictionaries;

    public DictionaryMenu() {
        scanner = new Scanner(System.in);
        dictionaries = new LinkedHashMap<>();
        initDictionaries();
    }

    private void initDictionaries() {
        addDictionary(new LatinDictionary(), new File("latin.txt"));
        addDictionary(new NumDictionary(), new File("num.txt"));
    }

    private void addDictionary(BaseDictionary dictionary, File file) {
        if(MenuUtils.checkFile(file)) {
           file = MenuUtils.createFile(file);
        }
        dictionaries.put(dictionary, file);
    }

    private BaseDictionary getDictionaryByClass(Class<? extends BaseDictionary> cl) {
        for(BaseDictionary dictionary : dictionaries.keySet()) {
            if(cl.isInstance(dictionary)) {
                return dictionary;
            }
        }
        return null;
    }


    private void fillDictionaries() {
        for(Map.Entry<BaseDictionary, File> entry : dictionaries.entrySet()) {
            try {
                entry.getKey().fillFromFile(entry.getValue().getAbsolutePath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Ошибка формата в " + entry.getValue().getName() + ". Словарь будет пустым");
                entry.getKey().getDictionary().clear();
            }
        }
    }


    public void run() {
        boolean isExit = false;
        BaseDictionary latin = getDictionaryByClass(LatinDictionary.class);
        BaseDictionary num = getDictionaryByClass(NumDictionary.class);
        fillDictionaries();
        while(!isExit) {
                MenuUtils.showMenu(MenuUtils.MAIN_MENU);
                switch (MenuUtils.readInput(scanner)) {
                    case "1" -> System.out.println("todo");
                    case "2" -> System.out.println("todo");
                    case "3" -> MenuUtils.showDictionaries(latin,num);
                    case "4" -> isExit = true;
                    default -> MenuUtils.showMessage("Нет такого действия");
            }
        }
    }





}
