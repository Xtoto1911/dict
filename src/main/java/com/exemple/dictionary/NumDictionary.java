package com.exemple.dictionary;

import com.exemple.utils.StringUtils;


public class NumDictionary extends BaseDictionary {

    @Override
    public boolean isValidKey(String key) {
        return StringUtils.matchesRegex(key, "\\d{5}");
    }
}
