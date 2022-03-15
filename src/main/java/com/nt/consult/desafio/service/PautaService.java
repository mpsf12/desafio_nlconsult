package com.nt.consult.desafio.service;

import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.util.ResultadoPautaEnum;

import java.util.List;

public interface PautaService {

    public List<Pauta> findAllPautas();

    public Pauta findPautaById(long id);

    public Pauta savePauta(Pauta pauta);

    public ResultadoPautaEnum contabilizarVotacaoPauta(long pautaId);
}
