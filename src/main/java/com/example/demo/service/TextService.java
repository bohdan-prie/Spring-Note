package com.example.demo.service;

import com.example.demo.entity.AbstractTextContainer;

import java.util.List;

public interface TextService {

    void deleteAll(String login);

    AbstractTextContainer create(String login);

    void deleteById(String id);

    <T extends AbstractTextContainer> void change(T note, String login);

    List<? extends AbstractTextContainer> getAll(String login);

    List<? extends AbstractTextContainer> getSortedByCreation(String login);

    List<? extends AbstractTextContainer> searchByPattern(String login, String pattern);
}