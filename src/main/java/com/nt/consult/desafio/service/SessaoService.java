package com.nt.consult.desafio.service;

import com.nt.consult.desafio.model.Sessao;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessaoService {

    public List<Sessao> findAllSessoes();

    public Sessao saveSessao(Sessao sessao);

    public ResponseEntity<Sessao> abrirSessaoVotacao(long pautaId);

    public ResponseEntity<Sessao> abrirSessaoVotacao(long pautaId, long duracao);
}
