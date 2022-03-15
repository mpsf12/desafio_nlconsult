package com.nt.consult.desafio.controller;

import java.util.*;

import com.nt.consult.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nt.consult.desafio.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;
        
    @GetMapping
    public List<User> findAllUsers() {
    	return (List<User>) userService.findAllUsers();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }
 
    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
    	return userService.saveUser(user);
    }
}
