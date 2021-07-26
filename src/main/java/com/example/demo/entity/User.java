package com.example.demo.entity;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Scope("session")
public class User {

    @Id
    private String login;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ToDoLine> toDoLines = new ArrayList<>();

    public User(String login, String password) {
        this.changeLogin(login);
        this.changePassword(password);
    }

    public User() {

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<ToDoLine> getToDoLines() {
        return toDoLines;
    }

    public void setToDoLines(List<ToDoLine> toDoLines) {
        if (toDoLines != null) {
            this.toDoLines = toDoLines;
        }
    }

    public void setNotes(List<Note> notes) {
        if (notes != null) {
            this.notes = notes;
        }
    }

    public void changeLogin(String login) {
        if (login != null && ! login.isEmpty()) {
            this.login = login;
        }
    }

    public void changePassword(String password) {
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }
}