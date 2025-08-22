package com.exemple.dictionary;

import com.exemple.utils.StringUtils;


public class LatinDictionary extends BaseDictionary {

    @Override
    public boolean isValidKey(String key) {
        return StringUtils.matchesRegex(key, "[A-Za-z]{4}");
    }
}
