package com.nt.consult.desafio.service.impl;

import com.nt.consult.desafio.exception.UserNotFoundException;
import com.nt.consult.desafio.model.User;
import com.nt.consult.desafio.repository.UserRepository;
import com.nt.consult.desafio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User findUserById(long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
