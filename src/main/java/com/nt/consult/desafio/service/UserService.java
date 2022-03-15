package com.nt.consult.desafio.service;

import com.nt.consult.desafio.dto.UserDto;
import com.nt.consult.desafio.model.User;

import java.util.List;

public interface UserService {

    public List<UserDto> findAllUsers();

    public UserDto findUserById(long id);

    public UserDto saveUser(UserDto userDto);
}
