package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = {"/user"})
public class UserController {

    private final static String TEMP_USER_LOGIN = "999";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void authenticate(@RequestBody User user) {
        User found = userService.find(user);
    }

    @PutMapping(params = {"login", "password"})
    public void create(@RequestParam String login, @RequestParam String password) {
        User user = new User(login, password);
        userService.create(user);
    }

    @PostMapping(params = {"password"})
    public void comparePassword(@RequestParam String password){
        User user = new User();
        user.changeLogin(TEMP_USER_LOGIN);
        User found = userService.find(user);
        if(! found.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(params = {"newLogin"})
    public void changeLogin(@RequestParam String newLogin) {
        userService.changeLogin(TEMP_USER_LOGIN, newLogin);
    }

    @PutMapping(params = {"password"})
    public void changePassword(@RequestParam String password) {
        userService.changePassword(TEMP_USER_LOGIN, password);
    }

    @DeleteMapping
    public void delete() {
        userService.delete(TEMP_USER_LOGIN);
    }
}