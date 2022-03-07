package com.nt.consult.desafio.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String cpf;

	public User(String name, String cpf) {
		super();
		this.name = name;
		this.cpf = cpf;
	}
}
