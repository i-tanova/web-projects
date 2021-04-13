package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void createNote(Note note){
        this.noteMapper.insert(note);
    }

    public void updateNote(Note note){
        this.noteMapper.updateNote(note);
    }

    public void deleteNote(int noteId){
        this.noteMapper.deleteNote(noteId);
    }

    public List<Note> getUserNotes(int userId){
        return this.noteMapper.getNotes(userId);
    }


    public Note getNoteById(int noteId){
        return this.noteMapper.getNoteById(noteId);
    }
}
