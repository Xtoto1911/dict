package com.exemple.dictionary;


import java.io.IOException;

public interface Dictionary {
    void fillFromFile(String path) throws IOException;

    void saveToFile(String path) throws IOException;

    void remove(String key);

    String find(String key);

    void add(String ket, String value);
}
