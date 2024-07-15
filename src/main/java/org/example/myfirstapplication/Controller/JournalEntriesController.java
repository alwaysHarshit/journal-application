package org.example.myfirstapplication.Controller;

import org.example.myfirstapplication.Dto.JournalEntryDto;
import org.example.myfirstapplication.Entity.JournalEntry;
import org.example.myfirstapplication.Service.JournalEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JournalEntriesController {
    @Autowired
    private JournalEntryServices journalEntryServices;

    @PostMapping("/save")
    public ResponseEntity<JournalEntryDto> saveEntry(@RequestBody JournalEntry saveJournalEntry) {
        return new ResponseEntity<>(journalEntryServices.save(saveJournalEntry), HttpStatus.OK);
    }

    @GetMapping("/viewall")
    public List<JournalEntry> viewAllEntry() {
        return journalEntryServices.getAllJournalEntry();
    }

    @GetMapping("view/{id}")
    public JournalEntryDto getById(@PathVariable Long id) {
        journalEntryServices.getJournalEntryById(id);
        return journalEntryServices.getJournalEntryById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteJournalEntry(@PathVariable Long id) {
        journalEntryServices.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteJournalEntry() {
        journalEntryServices.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<JournalEntryDto> update(@RequestBody JournalEntry journalEntry, @PathVariable Long id) {
        return new ResponseEntity<>(journalEntryServices.updateJournalById(journalEntry, id), HttpStatus.OK);
    }

    @PutMapping("updatebytitle/{title}")
    public ResponseEntity<JournalEntryDto> update(@RequestBody JournalEntry journalEntry,@PathVariable String title){
        return new ResponseEntity<>(journalEntryServices.updateJournalByTitle(journalEntry,title),HttpStatus.OK);
    }

}
