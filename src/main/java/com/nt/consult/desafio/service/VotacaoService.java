package com.nt.consult.desafio.service;

import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.util.VotacaoEnum;

import java.io.IOException;
import java.util.List;

public interface VotacaoService {

    public List<Votacao> findAllVotacaos();

    public Votacao findVotacaoById(long id) throws Exception;

    public Votacao votarPauta(long userId, long pautaId, VotacaoEnum voto) throws IOException;

    public Votacao saveVotacao(Votacao votacao);
}
