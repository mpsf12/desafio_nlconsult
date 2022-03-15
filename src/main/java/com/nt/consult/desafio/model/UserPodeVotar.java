package com.nt.consult.desafio.model;

import com.nt.consult.desafio.enums.UserPodeVotarEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPodeVotar {

    private UserPodeVotarEnum status;

}
