package com.nt.consult.desafio.model;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sessao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
