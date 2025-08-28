package com.exemple.service;

import com.exemple.validator.DictionaryValidator;
import com.exemple.dto.DictionaryEntryDto;
import com.exemple.model.DictionaryEntry;
import com.exemple.repository.DictionaryRepository;
import com.exemple.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final DictionaryValidator validator;

    public DictionaryService(DictionaryRepository dictionaryRepository, DictionaryValidator validator) {
        this.dictionaryRepository = dictionaryRepository;
        this.validator = validator;
    }

    public List<DictionaryEntry> getAllEntries() {
        List<DictionaryEntry> entries = dictionaryRepository.findAll();
        for(DictionaryEntry entry : entries) {
            entry.setValues(dictionaryRepository.findValuesByEntryId(entry.getId()));
        }
        return entries;
    }

    public List<DictionaryEntry> getEntriesByType(String type) {
        List<DictionaryEntry> entries = dictionaryRepository.findByType(type);
        for(DictionaryEntry entry : entries) {
            entry.setValues(dictionaryRepository.findValuesByEntryId(entry.getId()));
        }
        return entries;
    }

    public Optional<DictionaryEntry> getEntryById(Long id) {
        Optional<DictionaryEntry> entryOptional = dictionaryRepository.findById(id);
        if(entryOptional.isPresent()) {
            DictionaryEntry entry = entryOptional.get();
            entry.setValues(dictionaryRepository.findValuesByEntryId(entry.getId()));
        }
        return entryOptional;
    }

    public List<DictionaryEntry> search(String query, String dictionaryType) {
        List<DictionaryEntry> entries = dictionaryRepository.search(query,dictionaryType);
        for(DictionaryEntry entry : entries) {
            entry.setValues(dictionaryRepository.findValuesByEntryId(entry.getId()));
        }
        return entries;
    }

    public DictionaryEntry createEntry(DictionaryEntryDto entryDto) {

        validator.validateEntry(entryDto);

        if(dictionaryRepository.existsByKeyAndType(entryDto.getKey(),entryDto.getDictionaryType())) {
            throw new IllegalArgumentException(
                    "Ключ '" + entryDto.getKey() + "' уже существует в словаре типа '" +
                    entryDto.getDictionaryType() + "'");
        }

        DictionaryEntry entry = new DictionaryEntry();
        entry.setKey(entryDto.getKey());
        entry.setDictionaryType(entryDto.getDictionaryType());

        Long entryId = dictionaryRepository.saveEntry(entry);
        entry.setId(entryId);

        if(entryDto.getValues() != null && !entryDto.getValues().isEmpty()) {
            List<String> normalValues = entryDto.getValues().stream().map(StringUtils::clean).toList();
            dictionaryRepository.saveValues(entryId, normalValues);
            entry.setValues(entryDto.getValues());
        }

        return entry;
    }

    public DictionaryEntry updateEntry(Long id, DictionaryEntryDto entryDto) {
        Optional<DictionaryEntry> exitsEntry = dictionaryRepository.findById(id);
        if(exitsEntry.isEmpty()) {
            throw new IllegalArgumentException("Слова с id = " + id + " не найдено");
        }

        validator.validateEntry(entryDto);
        if(dictionaryRepository.existsByKeyAndTypeExcludingId(
                entryDto.getKey(), entryDto.getDictionaryType(), id)) {
            throw new IllegalArgumentException(
                    "Ключ '" + entryDto.getKey() + "' уже существует в словаре типа '" +
                    entryDto.getDictionaryType() + "'");
        }

        dictionaryRepository.updateEntry(id, entryDto.getKey(), entryDto.getDictionaryType());

        dictionaryRepository.deleteValues(id);
        if(entryDto.getValues() != null && !entryDto.getValues().isEmpty()) {
            List<String> normalValues = entryDto.getValues().stream().map(StringUtils::clean).toList();
            dictionaryRepository.saveValues(id, normalValues);
        }

        return getEntryById(id).orElseThrow();
    }

    public void deleteEntry(Long id) {
        if(!dictionaryRepository.existsById(id)) {
            throw new IllegalArgumentException("Запись с id = " + id + "' не найдена");
        }
        dictionaryRepository.deleteEntry(id);
    }

    public void deleteValue(Long entryId, String value) {
        if(!dictionaryRepository.existsById(entryId)) {
            throw new IllegalArgumentException("Запись с id = " + entryId + "' не найдена");
        }
        dictionaryRepository.deleteValue(entryId, value);
    }
}
