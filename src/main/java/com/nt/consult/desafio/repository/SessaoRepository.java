package com.nt.consult.desafio.repository;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;

public interface SessaoRepository extends CrudRepository<Sessao, Long> {

	Optional<Sessao> findByPauta(Pauta pauta);
}
