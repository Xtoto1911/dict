package com.exemple.model;

import java.util.List;

public class DictionaryEntry {
    private Long id;
    private String key;
    private List<String> values;
    private String dictionaryType;

    public DictionaryEntry() {
    }

    public DictionaryEntry(String key, List<String> values, String dictionaryType) {
        this.key = key;
        this.values = values;
        this.dictionaryType = dictionaryType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "DictionaryEntry{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", values=" + values +
                ", dictionaryType='" + dictionaryType + '\'' +
                '}';
    }
}
