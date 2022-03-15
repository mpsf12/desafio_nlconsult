package com.nt.consult.desafio.service.impl;

import com.nt.consult.desafio.dto.UserDto;
import com.nt.consult.desafio.exception.UserNotFoundException;
import com.nt.consult.desafio.model.User;
import com.nt.consult.desafio.repository.UserRepository;
import com.nt.consult.desafio.service.UserService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAllUsers() {
        return ((List<User>) userRepository.findAll())
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
    }

    public UserDto findUserById(long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return new UserDto(user.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    public UserDto saveUser(UserDto userDto) {
        return new UserDto(userRepository.save(dtoToEntity(userDto)));
    }

    private User dtoToEntity(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
}
