package com.nt.consult.desafio.service;

import com.nt.consult.desafio.dto.PautaDto;
import com.nt.consult.desafio.enums.ResultadoPautaEnum;

import java.util.List;

public interface PautaService {

    public List<PautaDto> findAllPautas();

    public PautaDto findPautaById(long id);

    public PautaDto savePauta(PautaDto pautaDto);

    public ResultadoPautaEnum contabilizarVotacaoPauta(long pautaId);
}
