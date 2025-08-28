package com.exemple.repository;

import com.exemple.model.DictionaryEntry;
import com.exemple.utils.DictionaryEntryMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DictionaryRepository {
    private final JdbcTemplate jdbcTemplate;

    public DictionaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DictionaryEntry> findAll() {
        String query = "select id, key, dictionary_type from dictionary_entries order by dictionary_type, key";
        return jdbcTemplate.query(query, new DictionaryEntryMapper());
    }

    public List<DictionaryEntry> findByType(String type) {
        String query = "select id, key, dictionary_type from dictionary_entries where dictionary_type = ? order by key";
        return jdbcTemplate.query(query, new DictionaryEntryMapper(), type);
    }

    public Optional<DictionaryEntry> findById(Long id) {
        String query = "select id, key, dictionary_type from dictionary_entries where id = ?";
        List<DictionaryEntry> results = jdbcTemplate.query(query, new DictionaryEntryMapper(), id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<String> findValuesByEntryId(Long id) {
        String query = "select value from dictionary_values where entry_id = ? order by id";
        return jdbcTemplate.queryForList(query, String.class, id);
    }

    public boolean existsById(Long id) {
        String query = "select count(*) from dictionary_entries where id = ?";
        Integer cnt = jdbcTemplate.queryForObject(query, Integer.class, id);
        return  cnt != null && cnt > 0;
    }

    public boolean existsByKeyAndType(String key, String dictionaryType) {
        String query = "select count(*) from dictionary_entries where key = ? and dictionary_type = ?";
        Integer cnt = jdbcTemplate.queryForObject(query, Integer.class, key, dictionaryType);
        return cnt != null && cnt > 0;
    }

    public boolean existsByKeyAndTypeExcludingId(String key, String dictionaryType, Long id) {
        String query = "select count(*) from dictionary_entries where key = ? and dictionary_type = ? and id != ?";
        Integer cnt = jdbcTemplate.queryForObject(query, Integer.class, key, dictionaryType, id);
        return cnt != null && cnt > 0;
    }

    public List<DictionaryEntry> search(String query, String dictionaryType) {
        String sqlQuery = "";
        Object[] params;

        if (dictionaryType != null && !dictionaryType.isEmpty()) {
            sqlQuery = "select distinct de.id, de.key, de.dictionary_type " +
                    "from dictionary_entries de " +
                    "left join dictionary_values dv on de.id = dv.entry_id " +
                    "where de.dictionary_type = ? and (de.key ilike ? or dv.value ilike ?) " +
                    "order by de.key";
            params = new Object[]{dictionaryType, "%" + query + "%", "%" + query + "%"};
        } else {
            sqlQuery = "select distinct de.id, de.key, de.dictionary_type " +
                    "from dictionary_entries de " +
                    "left join dictionary_values dv on de.id = dv.entry_id " +
                    "where de.key ilike ? or dv.value ilike ? " +
                    "order by de.dictionary_type, de.key";
            params = new Object[]{"%" + query + "%", "%" + query + "%"};
        }

        return jdbcTemplate.query(sqlQuery, new DictionaryEntryMapper(), params);
    }

    public Long saveEntry(DictionaryEntry entry) {
        String query = "insert into dictionary_entries (key, dictionary_type) values (?, ?) returning id";
        return jdbcTemplate.queryForObject(query, Long.class, entry.getKey(), entry.getDictionaryType());
    }

    public void saveValues(Long entryId, List<String> values) {
        String query = "insert into dictionary_values (entry_id, value) values(?, ?)";
        List<Object[]> bathArgs = new ArrayList<>();
        for(String value : values) {
            bathArgs.add(new Object[]{entryId, value});
        }
        jdbcTemplate.batchUpdate(query, bathArgs);
    }

    public void updateEntry(Long id, String key, String dictionaryType) {
        String query = "update dictionary_entries set key = ?, dictionary_type = ? where id = ?";
        jdbcTemplate.update(query, key, dictionaryType, id);
    }

    public void deleteValues(Long entryId) {
        String query = "delete from dictionary_values where entry_id = ?";
        jdbcTemplate.update(query, entryId);
    }

    public void deleteValue(Long entryId, String value) {
        String query = "delete from dictionary_values where entry_id = ? and value = ?";
        jdbcTemplate.update(query, entryId, value);
    }

    public void deleteEntry(Long id) {
        deleteValues(id);
        String query = "delete from dictionary_entries where id = ?";
        jdbcTemplate.update(query, id);
    }
}
