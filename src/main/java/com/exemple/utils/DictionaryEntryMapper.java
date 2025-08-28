package com.exemple.utils;

import com.exemple.model.DictionaryEntry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DictionaryEntryMapper implements RowMapper<DictionaryEntry> {

    @Override
    public DictionaryEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        DictionaryEntry entry = new DictionaryEntry();
        entry.setId(rs.getLong("id"));
        entry.setKey(rs.getString("key"));
        entry.setDictionaryType(rs.getString("dictionary_type"));
        return entry;
    }
}
