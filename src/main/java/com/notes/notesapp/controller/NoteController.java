package com.notes.notesapp.controller;

import com.notes.notesapp.domain.Note;
import com.notes.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin("*")
public class NoteController {

    private NoteService service;

    @Autowired
    public NoteController(NoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Note> getNotes() {
        return service.getNotes();
    }

    @GetMapping(params = "id")
    public Note getNote(@RequestParam(required = false) String id) {
        return service.getNote(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createNote(@RequestBody Note note) {
        service.createNote(note);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateNote(@RequestBody Note note) {
        service.createNote(note);
    }

    @DeleteMapping(params = "id")
    public void deleteNote(@RequestParam String id) {
        service.deleteNote(id);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteNote(@RequestBody Note note) throws NoteNotFoundException {
        if (note == null) {
            throw new NoteNotFoundException();
        }
        service.deleteNote(note);
    }

    @PatchMapping(params = "id", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void patchNote(@RequestParam String id, @RequestBody Map< String, Object> updates) throws NoteNotFoundException {
        Note note = service.getNote(id);
        if (note == null) {
            throw new NoteNotFoundException();
        }
        service.partialUpdate(note, updates);
    }

}
