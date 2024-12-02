package com.example.wow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wow.model.UserJPA;
import com.example.wow.repository.RepositoryJPA;

@RestController
@RequestMapping("/wow")
public class ControllerJPA {
    @Autowired
    RepositoryJPA repositoryJPA;
    
    @GetMapping("/user-jpa")
    public ResponseEntity<List<UserJPA>> getAllUser() {
        List<UserJPA> users = repositoryJPA.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/user-jpa/{id}")
    public ResponseEntity<UserJPA> getUserById(@PathVariable Integer id) {
        UserJPA user = repositoryJPA.findById(id).orElse(null);
        return ResponseEntity.ok().body(user);
    }
}
