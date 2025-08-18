package com.exemple;

import java.io.IOException;

public interface Dictionary {

    boolean add(String key, String val);

    String remove(String key);

    String search(String key);

    void fill(String path) throws IOException;
}
