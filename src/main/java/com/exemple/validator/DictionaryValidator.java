package com.exemple.validator;

import com.exemple.dto.DictionaryEntryDto;
import com.exemple.utils.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DictionaryValidator {
    public void validateEntry(DictionaryEntryDto entryDto) {
        if(entryDto.getKey() == null || entryDto.getKey().isBlank()) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        if(entryDto.getDictionaryType() == null || entryDto.getDictionaryType().isBlank()) {
            throw new IllegalArgumentException("Тип словаря не может быть пустым");
        }

        if(!isValidKey(entryDto.getKey(), entryDto.getDictionaryType())) {
            throw new IllegalArgumentException(
                    "Формат ключа '" + entryDto.getKey() + "' не подходит для словаря типа '" +
                            entryDto.getDictionaryType() + "'"
            );
        }

        if(entryDto.getValues() != null && !entryDto.getValues().isEmpty()){
            for(String value : entryDto.getValues()) {
                if(value == null || value.isBlank()) {
                    throw new IllegalArgumentException("Значение не может быть пустым");
                }
                if(!StringUtils.checkValue(value)) {
                    throw new IllegalArgumentException(
                            "Формат значения '" + value + "' не подходит"
                    );
                }
            }
        }
    }

    private boolean isValidKey(String key, String dictionaryType) {
        return switch (dictionaryType.toLowerCase()) {
            case "latin" -> StringUtils.matchesRegex(key, "[A-Za-z]{4}");
            case "num" -> StringUtils.matchesRegex(key, "\\d{5}");
            default -> throw new IllegalArgumentException("Неизвестный тип словаря: " + dictionaryType);
        };
    }
}
