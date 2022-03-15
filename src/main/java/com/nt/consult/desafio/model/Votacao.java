package com.nt.consult.desafio.model;

import javax.persistence.*;

import com.nt.consult.desafio.enums.VotacaoEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Votacao {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@ManyToOne
	@JoinColumn(name = "pauta_id", referencedColumnName = "id")
	private Pauta pauta;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	private VotacaoEnum voto;

	public Votacao(Pauta pauta, User user, VotacaoEnum voto) {
		super();
		this.pauta = pauta;
		this.user = user;
		this.voto = voto;
	}

}
