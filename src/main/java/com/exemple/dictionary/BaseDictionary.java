package com.exemple.dictionary;

import com.exemple.utils.StringUtils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseDictionary implements Dictionary {

    protected final Map<String, String> dictionary = new LinkedHashMap<>();
    protected final String separator = " - ";

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    @Override
    public void fillFromFile(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            while (StringUtils.isNotEmpty(line)) {
                String[] pair = line.split(separator, 2);
                String key = StringUtils.normalize(pair[0]);
                if (pair.length == 2) {
                    add(StringUtils.clean(pair[0]), StringUtils.clean(pair[1]));
                } else {
                    throw new IOException("Строка " + line + " не соответствует формату \"ключ - значение\"");
                }
                line = bufferedReader.readLine();
            }
        }
    }

    @Override
    public void saveToFile(String path) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(toString());
        }
    }

    @Override
    public void remove(String key) {
        if (!dictionary.containsKey(key)) {
            throw new IllegalArgumentException("Слово не найдено");
        }
        dictionary.remove(key);
    }

    @Override
    public String find(String key) {
        if (!dictionary.containsKey(key)) {
            throw new IllegalArgumentException("Слово не найдено");
        }
        return dictionary.get(key);
    }

    @Override
    public void add(String key, String value) {
        if (!isValidKey(key) || dictionary.containsKey(key)) {
            throw new IllegalArgumentException("Формат слова " + key + " не подходит для данного словаря");
        }
        dictionary.put(key,value);
    }

    public abstract boolean isValidKey(String key);

    @Override
    public String toString() {
        if (dictionary.isEmpty()) {
            return "Словарь пуст";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            builder.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }

        return builder.toString();
    }
}
