package com.nt.consult.desafio.controller;

import java.util.*;

import com.nt.consult.desafio.dto.UserDto;
import com.nt.consult.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;
        
    @GetMapping
    public List<UserDto> findAllUsers() {
    	return (List<UserDto>) userService.findAllUsers();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }
 
    @PostMapping
    public UserDto saveUser(@Validated @RequestBody UserDto userDto) {
    	return userService.saveUser(userDto);
    }
}
