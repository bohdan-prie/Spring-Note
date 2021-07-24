package com.example.demo.controller;

import com.example.demo.entity.AbstractTextContainer;
import com.example.demo.entity.Note;
import com.example.demo.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final static String TEMP_USER_LOGIN = "999";

    private final TextService textService;

    @Autowired
    public NotesController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping(path = "/all")
    public List<? extends AbstractTextContainer> getAll() {
        return textService.getAll(TEMP_USER_LOGIN);
    }

    @GetMapping(path = "/sortedCreation")
    public List<? extends AbstractTextContainer> sortedByCreation(){
        return textService.getSortedByCreation(TEMP_USER_LOGIN);
    }

    @GetMapping(params = {"q"})
    public List<? extends AbstractTextContainer> searchByPattern(@RequestParam String q) {
        return textService.searchByPattern(TEMP_USER_LOGIN ,q);
    }

    @PostMapping
    public String add() {
        AbstractTextContainer textContainer = textService.create(TEMP_USER_LOGIN);
        return textContainer.getId();
    }

    @PostMapping(params = {"action"})
    public void save(@RequestBody Note note) {
        textService.change(note, TEMP_USER_LOGIN);
    }

    @DeleteMapping
    public void deleteAll() {
        textService.deleteAll(TEMP_USER_LOGIN);
    }

    @DeleteMapping(params = {"id"})
    public void delete(@RequestParam String id) {
        textService.deleteById(id);
    }
}