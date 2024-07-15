package org.example.myfirstapplication.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter@Setter
@Component
public class JournalEntryDto{
    private Long id;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
}
