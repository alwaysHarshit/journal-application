package org.example.myfirstapplication.Repository;

import org.example.myfirstapplication.Entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry,Long> {
    boolean existsByTitle(String title);
    Optional<JournalEntry> getJournalEntryByTitle(String title);
}
