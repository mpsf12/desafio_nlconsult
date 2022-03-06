package com.nt.consult.desafio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Votacao;

@Repository
public interface VotacaoRepository extends CrudRepository<Votacao, Long> {

	public List<Votacao> findByPauta(Pauta pauta);
}
