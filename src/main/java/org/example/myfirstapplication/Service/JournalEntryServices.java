package org.example.myfirstapplication.Service;

import org.example.myfirstapplication.Dto.JournalEntryDto;
import org.example.myfirstapplication.Entity.JournalEntry;
import org.example.myfirstapplication.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Component
public class JournalEntryServices {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private JournalEntryDto journalEntryDto;

    public JournalEntryDto save(JournalEntry saveJournalEntry) {
        saveJournalEntry.setId(new Random().nextLong(100, 999));
        saveJournalEntry.setTitle(uniqueTitle(saveJournalEntry.getTitle()));
        saveJournalEntry.setLocalDateTime(LocalDateTime.now());
        journalEntryRepository.save(saveJournalEntry);
        return convertDto(saveJournalEntry);
    }

    private String uniqueTitle(String getTitle) {
        if (getTitle==null||getTitle.trim().isEmpty()){
            getTitle="untitled";
        }
        String baseTitle=getTitle.trim();
        String uniqueTitle=baseTitle;
        int counter=1;
        while (journalEntryRepository.existsByTitle(uniqueTitle)){
            uniqueTitle= STR."\{baseTitle} \{counter}";
            counter++;
        }
        return uniqueTitle;
    }

    public List<JournalEntry> getAllJournalEntry() {
        return journalEntryRepository.findAll();
    }

    public JournalEntryDto getJournalEntryById(Long id) {
        Optional<JournalEntry> entry=journalEntryRepository.findById(id);
        if (entry.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"NO ID PRESENT");
        }
        return convertDto(entry.get());
    }
    private JournalEntryDto convertDto(JournalEntry journalEntry) {
        journalEntryDto.setId(journalEntry.getId());
        journalEntryDto.setTitle(journalEntry.getTitle());
        journalEntryDto.setContent((journalEntry.getContent()));
        journalEntryDto.setLocalDateTime(journalEntry.getLocalDateTime());
        return journalEntryDto;
    }

    public void delete(Long id) {
        journalEntryRepository.deleteById(id);
    }

    public void deleteAll() {
        journalEntryRepository.deleteAll();
    }

    public JournalEntryDto updateJournalById(JournalEntry newEntry, Long id) {
        Optional<JournalEntry> oldEntry = journalEntryRepository.findById(id);

        if (oldEntry.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"NO ID FOUND");
        }
        oldEntry.get().setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.get().getTitle());
        oldEntry.get().setContent(newEntry.getContent() != null ? newEntry.getContent() : oldEntry.get().getContent());
        oldEntry.get().setLocalDateTime(LocalDateTime.now());
        journalEntryRepository.save(oldEntry.get());
        return convertDto(oldEntry.get());

    }

    public JournalEntryDto updateJournalByTitle(JournalEntry newEntry, String title) {
        Optional<JournalEntry> oldEntry = journalEntryRepository.getJournalEntryByTitle(title);
        if (oldEntry.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        oldEntry.get().setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.get().getTitle());
        oldEntry.get().setContent(newEntry.getContent() != null ? newEntry.getContent() : oldEntry.get().getContent());
        oldEntry.get().setLocalDateTime(LocalDateTime.now());
        journalEntryRepository.save(oldEntry.get());
        return convertDto(oldEntry.get());
    }
}
