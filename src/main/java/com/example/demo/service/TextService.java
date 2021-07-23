package com.example.demo.service;

import com.example.demo.entity.AbstractTextContainer;
import com.example.demo.entity.Note;

import java.util.List;

public interface TextService {

    public void deleteAll(String login);

    public AbstractTextContainer create(String login);

    public void deleteById(String id);

    public void change(Note note, String login);

    public List<? extends AbstractTextContainer> getAll(String login);

    public List<? extends AbstractTextContainer> getSortedByCreation(String login);

    public List<? extends AbstractTextContainer> searchByPattern(String login, String pattern);
}