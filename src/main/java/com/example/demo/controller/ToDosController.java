package com.example.demo.controller;

import com.example.demo.entity.AbstractTextContainer;
import com.example.demo.entity.Note;
import com.example.demo.entity.ToDoLine;
import com.example.demo.service.TextService;
import com.example.demo.service.impl.NoteService;
import com.example.demo.service.impl.ToDoLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/toDos"})
public class ToDosController {

    private final static String TEMP_USER_LOGIN = "999";

    private final ToDoLineService service;

    @Autowired
    public ToDosController(ToDoLineService service) {
        this.service = service;
    }

    @GetMapping(path = "/all")
    public List<? extends AbstractTextContainer> getAll() {
        return service.getAll(TEMP_USER_LOGIN);
    }

    @GetMapping(path = "/sortedCreation")
    public List<? extends AbstractTextContainer> sortedByCreation(){
        return service.getSortedByCreation(TEMP_USER_LOGIN);
    }

    @GetMapping(params = {"q"})
    public List<? extends AbstractTextContainer> searchByPattern(@RequestParam String q) {
        return service.searchByPattern(TEMP_USER_LOGIN ,q);
    }

    @PostMapping
    public String add() {
        AbstractTextContainer textContainer = service.create(TEMP_USER_LOGIN);
        return textContainer.getId();
    }

    @PostMapping(path = "/save")
    public void saveToDoLine(@RequestBody ToDoLine toDoLine) {
        service.change(toDoLine, TEMP_USER_LOGIN);
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll(TEMP_USER_LOGIN);
    }

    @DeleteMapping(params = {"id"})
    public void delete(@RequestParam String id) {
        service.deleteById(id);
    }
}