package com.nt.consult.desafio.service;

import com.nt.consult.desafio.dto.SessaoDto;

import java.util.List;

public interface SessaoService {

    public List<SessaoDto> findAllSessoes();

    public SessaoDto saveSessao(SessaoDto sessaoDto);

    public SessaoDto abrirSessaoVotacao(long pautaId);

    public SessaoDto abrirSessaoVotacao(long pautaId, long duracao);
}
