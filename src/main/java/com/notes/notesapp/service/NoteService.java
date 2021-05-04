package com.notes.notesapp.service;

import com.notes.notesapp.domain.Note;
import com.notes.notesapp.repository.NoteRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    private static Logger LOGGER = LoggerFactory.getLogger(NoteService.class);
    private NoteRepo repo;

    @Autowired
    public NoteService(NoteRepo repo) {
        this.repo = repo;
    }

    public boolean createNote(Note note) {
        LOGGER.info("Creating note");
        if (note.getDateTime() == null) {
            note.setDateTime(LocalDateTime.now());
        }
        repo.save(note);
        return true;
    }

    public boolean deleteNote(String id) {
        LOGGER.info("Deleting note");
        repo.deleteById(id);
        return true;
    }

    public boolean deleteNote(Note note) {
        LOGGER.info("Deleting note");
        repo.delete(note);
        return true;
    }

    public Note getNote(String id) {
        LOGGER.info("Getting note");
        return repo.findById(id).orElse(null);
    }

    public List<Note> getNotes() {
        LOGGER.info("Getting note");
        return repo.findAll();
    }

    public boolean updateNote(Note note) {
        LOGGER.info("Updating note");
        repo.save(note);
        return true;
    }

}
