package com.nt.consult.desafio.model;

import javax.persistence.*;

import com.nt.consult.desafio.util.VotacaoEnum;

@Entity
@Table(name = "votacao")
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
	
	

	public Votacao() {
	}

	public Votacao(Pauta pauta, User user, VotacaoEnum voto) {
		this.pauta = pauta;
		this.user = user;
		this.voto = voto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VotacaoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotacaoEnum voto) {
		this.voto = voto;
	}
	
	
	
}
