package com.nt.consult.desafio.dto;

import com.nt.consult.desafio.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String name;
    private String cpf;

    public UserDto(String name, String cpf) {
        super();
        this.name = name;
        this.cpf = cpf;
    }

    public UserDto(User user){
        BeanUtils.copyProperties(user, this);
    }
}
