package com.exemple.dictionary;

import com.exemple.utils.StringUtils;

import java.io.IOException;

public class LatinDictionary extends BaseDictionary {
    public LatinDictionary(String path) throws IOException {
        super(path);
    }

    @Override
    public boolean isValidKey(String key) {
        return StringUtils.matchesRegex(key,"[A-Za-z]{4}");
    }
}
