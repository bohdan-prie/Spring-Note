package com.example.demo.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Scope("session")
public class User {
    private static final Logger LOG = LogManager.getLogger(User.class.getName());

    @Id
    private String login;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

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

    public void setNotes(List<Note> notes) {
        if (notes == null) {
            LOG.debug("Notes list is null");
        } else {
            this.notes = notes;
        }
    }

    public void changeLogin(String login) {
        if (login == null || login.isEmpty()) {
            LOG.debug("Login is null or empty");
        } else {
            this.login = login;
        }
    }

    public void changePassword(String password) {
        if (password == null || password.isEmpty()) {
            LOG.debug("Password is null or empty");
        } else {
            this.password = password;
        }
    }
}
