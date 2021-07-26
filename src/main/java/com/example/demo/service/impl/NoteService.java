package com.example.demo.service.impl;

import com.example.demo.entity.AbstractTextContainer;
import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NoteService implements TextService {

    private final NoteRepository repository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    @Override
    public List<Note> getAll(String login) {
        return repository.findByUser_LoginOrderByTimeChange(login);
    }

    @Override
    public List<Note> getSortedByCreation(String login){
        return repository.findByUser_LoginOrderByTimeCreation(login);
    }

    @Override
    public void deleteAll(String login) {
        repository.deleteAllByUserLogin(login);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void change(AbstractTextContainer textContainer, String login) {
        if(textContainer instanceof Note){
            changeNote((Note) textContainer, login);
        }
    }

    private void changeNote(Note note, String login){
        User user = new User();
        user.changeLogin(login);
        note.setUser(user);
        repository.save(note);
    }

    @Override
    public Note create(String login) {
        User user = new User();
        user.changeLogin(login);
        Note note = new Note("Title", "Note", user);
        return repository.save(note);
    }

    @Override
    public List<Note> searchByPattern(String login, String pattern) {
        return repository.findByPattern(login, pattern, pattern);
    }
}