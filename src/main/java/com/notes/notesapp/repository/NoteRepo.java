package com.notes.notesapp.repository;


import com.notes.notesapp.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepo extends MongoRepository<Note, String> {

}
