package com.nt.consult.desafio.service;

import com.nt.consult.desafio.dto.VotacaoDto;
import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.enums.VotacaoEnum;

import java.io.IOException;
import java.util.List;

public interface VotacaoService {

    public List<VotacaoDto> findAllVotacaos();

    public VotacaoDto findVotacaoById(long id) throws Exception;

    public VotacaoDto votarPauta(long userId, long pautaId, VotacaoEnum voto) throws IOException;

    public VotacaoDto saveVotacao(VotacaoDto votacao);
}
