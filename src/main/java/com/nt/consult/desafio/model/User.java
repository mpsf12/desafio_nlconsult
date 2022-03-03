package com.nt.consult.desafio.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String cpf;
    
    public User() {}
    
    public User(long id, String name, String cpf) {
    	this.id = id;
    	this.name = name;
    	this.cpf = cpf;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    
    
    
}
