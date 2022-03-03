package com.nt.consult.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nt.consult.desafio.model.Pauta;

@Repository
public interface PautaRepository extends CrudRepository<Pauta, Long> {

}
