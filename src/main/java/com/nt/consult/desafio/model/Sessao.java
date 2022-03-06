package com.nt.consult.desafio.model;

import java.sql.Date;

import javax.persistence.*;

import org.springframework.http.ResponseEntity;

import com.nt.consult.desafio.exception.SessaoEncerradaException;

@Entity
@Table(name = "sessao")
public class Sessao {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	@Column(name = "abertura_sessao")
	private long aberturaSessao; //tempo da abertura assim como a duração serão guardados em milissegundos
	private long duracao;
	@OneToOne
	@JoinColumn(name = "pauta_id", referencedColumnName = "id")
	private Pauta pauta;
	
	public Sessao() {}
	
	public Sessao(long aberturaSessao, long duracao, Pauta pauta) {
		this.aberturaSessao = aberturaSessao;
		this.duracao = duracao;
		this.pauta = pauta;
	}


	public boolean sessaoFinalizada() {
		long agora = System.currentTimeMillis();
		if(agora - getAberturaSessao() <= getDuracao()) {
			return false;
		} else {
			return true;
		}
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAberturaSessao() {
		return aberturaSessao;
	}
	public void setAberturaSessao(long aberturaSessao) {
		this.aberturaSessao = aberturaSessao;
	}
	public long getDuracao() {
		return duracao;
	}
	public void setDuracao(long duracao) {
		this.duracao = duracao;
	}	
}
