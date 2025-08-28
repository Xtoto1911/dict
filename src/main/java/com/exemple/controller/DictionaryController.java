package com.exemple.controller;

import com.exemple.dto.DictionaryEntryDto;
import com.exemple.model.DictionaryEntry;
import com.exemple.service.DictionaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryController {
    public final DictionaryService service;

    public DictionaryController(DictionaryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DictionaryEntry>> getAllEntries() {
        return ResponseEntity.ok(service.getAllEntries());
    }

    @GetMapping("/type/{dictionaryType}")
    public ResponseEntity<List<DictionaryEntry>> getEntriesByType(@PathVariable("dictionaryType") String dictionaryType) {
        return ResponseEntity.ok(service.getEntriesByType(dictionaryType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DictionaryEntry> getEntryById(@PathVariable("id") Long id) {
        Optional<DictionaryEntry> entry = service.getEntryById(id);
        //написал я
        //if(entry.isEmpty()) {
        //    return ResponseEntity.notFound().build();
        //}
        //return ResponseEntity.ok(entry.get());

        //IDE сказала так лучше, оставил так
        return entry.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DictionaryEntry>> search(
            @RequestParam String query,
            @RequestParam(required = false) String dictionaryType) {
        return ResponseEntity.ok(service.search(query, dictionaryType));
    }

    @PostMapping
    public ResponseEntity<DictionaryEntry> createEntry(@RequestBody DictionaryEntryDto entryDto) {
        DictionaryEntry entry = service.createEntry(entryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DictionaryEntry> updateEntry(
            @PathVariable("id") Long id,
            @RequestBody DictionaryEntryDto entryDto) {
        try {
            DictionaryEntry entry = service.updateEntry(id, entryDto);
            return ResponseEntity.ok(entry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable("id") Long id) {
        try {
            service.deleteEntry(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{entryId}/values")
    public ResponseEntity<Void> deleteValue(
            @PathVariable("entryId") Long entryId,
            @RequestParam String value) {
        try {
            service.deleteValue(entryId, value);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
