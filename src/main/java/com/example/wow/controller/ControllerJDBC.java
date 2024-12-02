package com.example.wow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wow.model.UserJDBC;
import com.example.wow.repository.RepositoryJDBC;

@RestController
@RequestMapping("/app")
public class ControllerJDBC {
    @Autowired
    RepositoryJDBC repositoryJDBC;

    @GetMapping("/users")
    public ResponseEntity<List<UserJDBC>> getAllUser() {
        List<UserJDBC> users = repositoryJDBC.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserJDBC> getUserById(@PathVariable Long id) {
        UserJDBC user = repositoryJDBC.findById(id);
        return ResponseEntity.ok().body(user);
    }
}
