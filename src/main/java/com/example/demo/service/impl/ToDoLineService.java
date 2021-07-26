package com.example.demo.service.impl;

import com.example.demo.entity.AbstractTextContainer;
import com.example.demo.entity.ToDoLine;
import com.example.demo.entity.User;
import com.example.demo.repository.ToDoLineRepository;
import com.example.demo.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoLineService implements TextService {

    private final ToDoLineRepository repository;

    @Autowired
    public ToDoLineService(ToDoLineRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteAll(String login) {
        repository.deleteAllByUserLogin(login);
    }

    @Override
    public ToDoLine create(String login) {
        User user = new User();
        user.changeLogin(login);
        ToDoLine toDoLine = new ToDoLine("Title", user);
        return repository.save(toDoLine);
    }


    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void change(AbstractTextContainer textContainer, String login) {
        if(textContainer instanceof ToDoLine){
            changeToDoLine((ToDoLine) textContainer, login);
        }
    }

    public void changeToDoLine(ToDoLine toDoLine, String login) {
        User user = new User();
        user.changeLogin(login);
        toDoLine.setUser(user);
        repository.save(toDoLine);
    }

    @Override
    public List<ToDoLine> getAll(String login) {
        return repository.findByUser_LoginOrderByTimeChange(login);
    }

    @Override
    public List<ToDoLine> getSortedByCreation(String login) {
        return repository.findByUser_LoginOrderByTimeCreation(login);
    }

    @Override
    public List<ToDoLine> searchByPattern(String login, String pattern) {
        List<ToDoLine> all = repository.findByUser_LoginOrderByTimeChange(login);
        List<ToDoLine> withPattern = all
                .stream()
                .filter(toDoLine -> toDoLine.getTitle().toLowerCase().contains(pattern.toLowerCase()) ||
                        toDoLine.getToDo()
                                .stream()
                                .anyMatch(toDo -> toDo.getBody().contains(pattern.toLowerCase())))
                .collect(Collectors.toList());
        return withPattern;
    }
}