package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user){
        userRepository.save(user);
    }

    public User find(User user){
        User found = userRepository.getOne(user.getLogin());
        if(! found.getPassword().equals(user.getPassword())){

        }
        return userRepository.getOne(user.getLogin());
    }

    public void delete(String login){
        userRepository.deleteById(login);
    }

    public void changePassword(String login, String password){
        User found = userRepository.getOne(login);
        found.changePassword(password);
        userRepository.save(found);
    }

    public void changeLogin(String currentLogin, String newLogin){
        User found = userRepository.getOne(currentLogin);
        found.changeLogin(newLogin);
        userRepository.save(found);
    }
}