package com.example.demo.controller;

import com.example.demo.entity.AbstractTextContainer;
import com.example.demo.entity.Note;
import com.example.demo.entity.ToDoLine;
import com.example.demo.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/notes", "/toDos"})
public class TextController {

    private final static String TEMP_USER_LOGIN = "999";

    private final TextService textService;

    @Autowired
    public TextController(TextService textService) {
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

    @PostMapping(path = "/saveNote")
    public void saveNote(@RequestBody Note note) {
        textService.change(note, TEMP_USER_LOGIN);
    }

    @PostMapping(path = "/saveToDo")
    public void saveToDoLine(@RequestBody ToDoLine toDoLine) {
        textService.change(toDoLine, TEMP_USER_LOGIN);
    }

    /*@PostMapping()
    public void saveNote (@RequestBody Note note){

    }*/

    @DeleteMapping
    public void deleteAll() {
        textService.deleteAll(TEMP_USER_LOGIN);
    }

    @DeleteMapping(params = {"id"})
    public void delete(@RequestParam String id) {
        textService.deleteById(id);
    }
}