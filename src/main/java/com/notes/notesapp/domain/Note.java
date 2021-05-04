package com.notes.notesapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {

    @Id
    private String id;
    private LocalDateTime dateTime;
    private String note;

    @Override
    public String toString() {
        return "Note :" +
                "at " + dateTime + ", note='" + note;
    }
}
