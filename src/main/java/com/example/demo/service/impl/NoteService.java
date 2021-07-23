package com.example.demo.service.impl;

import com.example.demo.repository.NoteRepository;
import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements TextService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAll(String login) {
        return noteRepository.findByUser_LoginOrderByTimeChange(login);
    }

    @Override
    public List<Note> getSortedByCreation(String login){
        return noteRepository.findByUser_LoginOrderByTimeCreation(login);
    }

    @Override
    public void deleteAll(String login) {
        noteRepository.deleteAllByUserLogin(login);
    }

    @Override
    public void deleteById(String id) {
        noteRepository.deleteById(id);
    }

    @Override
    public void change(Note note, String login) {
        User user = new User();
        user.changeLogin(login);
        note.setUser(user);
        noteRepository.save(note);
    }

    @Override
    public Note create(String login) {
        User user = new User();
        user.changeLogin(login);
        Note note = new Note("Title", "Note", user);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> searchByPattern(String login, String pattern) {
        return noteRepository.findByUser_LoginAndTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(login, pattern, pattern);
    }
}