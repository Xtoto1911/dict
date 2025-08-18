package com.exemple.dictionary;

import com.exemple.utils.StringUtils;

import java.io.IOException;

public class NumDictionary extends BaseDictionary {
    public NumDictionary(String path) throws IOException {
        super(path);
    }

    @Override
    public boolean isValidKey(String key) {
        return StringUtils.matchesRegex(key, "\\d{5}");
    }
}
