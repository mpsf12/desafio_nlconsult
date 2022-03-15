package com.nt.consult.desafio.service;

import com.nt.consult.desafio.dto.SessaoDto;
import com.nt.consult.desafio.model.Sessao;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SessaoService {

    public List<SessaoDto> findAllSessoes();

    public SessaoDto saveSessao(SessaoDto sessaoDto);

    public SessaoDto abrirSessaoVotacao(long pautaId);

    public SessaoDto abrirSessaoVotacao(long pautaId, long duracao);
}
