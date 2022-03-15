package com.nt.consult.desafio.service.impl;

import com.nt.consult.desafio.dto.PautaDto;
import com.nt.consult.desafio.exception.PautaNotFoundException;
import com.nt.consult.desafio.exception.SessaoNaoFinalizadaException;
import com.nt.consult.desafio.exception.SessaoNotFoundException;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.repository.VotacaoRepository;
import com.nt.consult.desafio.service.PautaService;
import com.nt.consult.desafio.enums.ResultadoPautaEnum;
import com.nt.consult.desafio.enums.VotacaoEnum;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;

@Service
@NoArgsConstructor
public class PautaServiceImpl implements PautaService {

    private static final Logger log = LoggerFactory.getLogger(PautaServiceImpl.class);

    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotacaoRepository votacaoRepository;
    @Autowired
    private SessaoRepository sessaoRepository;

    public List<PautaDto> findAllPautas() {
        return ((ArrayList<Pauta>) this.pautaRepository.findAll())
                .stream()
                .map(pauta -> new PautaDto(pauta))
                .collect(Collectors.toList());
    }

    public PautaDto findPautaById(long id){
        Optional<Pauta> pauta = pautaRepository.findById(id);

        if (pauta.isPresent()) {
            return new PautaDto(pauta.get());
        } else {
            throw new PautaNotFoundException("Não foi possível encontrar a pauta de id " + id);
        }
    }

    public PautaDto savePauta(PautaDto pauta) {
        return new PautaDto(pautaRepository.save(dtoToEntity(pauta)));
    }

    public ResultadoPautaEnum contabilizarVotacaoPauta(long pautaId) {
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);

        if (pauta.isPresent()) {
            Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
            if (sessao.isPresent()) {
                if (!sessao.get().sessaoFinalizada()) {
                    throw new SessaoNaoFinalizadaException(
                            "Sessao de votação da Pauta de id " + pautaId + " ainda nao foi finalizada.");
                }
            } else {
                throw new SessaoNotFoundException("Não foi possivel encontrar a sessão da Pauta de id " + pautaId );
            }
            List<Votacao> votacao = votacaoRepository.findByPauta(pauta.get());
            int sim = 0, nao = 0;
            for (Votacao voto : votacao) {
                if (voto.getVoto() == VotacaoEnum.Sim) {
                    sim++;
                } else {
                    nao++;
                }
            }
            // Julguei que a pauta so sera aprovada se houver a maioria dos votos a favor,
            // ou seja, o empate resultada em ela sendo reprovada
            if (sim > nao) {
                return ResultadoPautaEnum.Aprovado;
            } else {
                return ResultadoPautaEnum.Reprovado;
            }
        } else {
            throw new PautaNotFoundException("Não foi possível encontrar a pauta de id " + pautaId);
        }
    }

    private Pauta dtoToEntity(PautaDto pautaDto){
        Pauta pauta = new Pauta();
        BeanUtils.copyProperties(pautaDto, pauta);
        return pauta;
    }
}
