package com.example.demo.controller;

import com.example.demo.entity.Note;
import com.example.demo.service.impl.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final static String TEMP_USER_LOGIN = "999";

    private final NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(path = "/all")
    public List<Note> getAll() {
        return noteService.getAll(TEMP_USER_LOGIN);
    }

    @GetMapping(path = "/sortedCreation")
    public List<Note> sortedByCreation(){
        return noteService.getSortedByCreation(TEMP_USER_LOGIN);
    }

    @GetMapping(params = {"q"})
    public List<Note> searchByPattern(@RequestParam String q) {
        return noteService.searchByPattern(TEMP_USER_LOGIN ,q);
    }

    @PostMapping
    public String add() {
        Note note = noteService.create(TEMP_USER_LOGIN);
        return note.getId();
    }

    @PostMapping(params = {"action"})
    public void save(@RequestBody Note note) {
        noteService.change(note, TEMP_USER_LOGIN);
    }

    @DeleteMapping
    public void deleteAll() {
        noteService.deleteAll(TEMP_USER_LOGIN);
    }

    @DeleteMapping(params = {"id"})
    public void delete(@RequestParam String id) {
        noteService.deleteById(id);
    }
}