package com.nt.consult.desafio.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "pauta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	private String descricao;

	public Pauta(String descricao) {
		super();
		this.descricao = descricao;
	}
}
