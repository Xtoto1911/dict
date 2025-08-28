package com.exemple.dto;

import java.util.List;

public class DictionaryEntryDto {
    private String key;
    private List<String> values;
    private String dictionaryType;

    public DictionaryEntryDto() {}

    public DictionaryEntryDto(String key, List<String> values, String dictionaryType) {
        this.key = key;
        this.values = values;
        this.dictionaryType = dictionaryType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    @Override
    public String toString() {
        return "DictionaryEntryDto{" +
                "key='" + key + '\'' +
                ", values=" + values +
                ", dictionaryType='" + dictionaryType + '\'' +
                '}';
    }
}
