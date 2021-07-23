package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class StaticController {

    @GetMapping("/")
    public String get() {
        return "/html/StartPage.html";
    }

    @GetMapping("/notes")
    public String getNotes() {
        return "/html/Notes.html";
    }

    @GetMapping("/reg")
    public String getReg() {
        return "/html/Registration.html";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "/html/Authorisation.html";
    }

    @GetMapping("/toDos")
    public String getToDos() {
        return "/html/ToDo.html";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "/html/Profile.html";
    }
}