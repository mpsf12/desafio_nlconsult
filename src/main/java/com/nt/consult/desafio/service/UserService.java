package com.nt.consult.desafio.service;

import com.nt.consult.desafio.model.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public User findUserById(long id);

    public User saveUser(User user);
}
