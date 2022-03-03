package com.nt.consult.desafio.model;

import javax.persistence.*;

@Entity
@Table(name = "pauta")
public class Pauta {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	private String descricao;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
