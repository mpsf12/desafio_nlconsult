package com.nt.consult.desafio.service.impl;

import com.nt.consult.desafio.dto.SessaoDto;
import com.nt.consult.desafio.exception.SessaoAlreadyCreatedException;
import com.nt.consult.desafio.exception.SessaoNotFoundException;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.service.SessaoService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class SessaoServiceImpl implements SessaoService {

    private static final Logger log = LoggerFactory.getLogger(SessaoServiceImpl.class);

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    public List<SessaoDto> findAllSessoes() {
        return ((List<Sessao>) sessaoRepository.findAll())
                .stream()
                .map(sessao -> new SessaoDto(sessao))
                .collect(Collectors.toList());
    }

    public SessaoDto saveSessao(SessaoDto sessaoDto) {
        return new SessaoDto(sessaoRepository.save(dtoToEntity(sessaoDto)));
    }

    public SessaoDto abrirSessaoVotacao(long pautaId) {
        return abrirSessaoVotacao(pautaId, 60000);
    }

    public SessaoDto abrirSessaoVotacao(long pautaId, long duracao){
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);

        if(pauta.isPresent()) {
            Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
            if(sessao.isPresent()) {
                throw new SessaoAlreadyCreatedException("Sessão de votação já criada para a pauta de id " + pautaId);
            }
            Sessao newSessao = new Sessao(System.currentTimeMillis(), duracao, pauta.get());
            sessaoRepository.save(newSessao);
            return new SessaoDto(newSessao);
        } else {
            throw new SessaoNotFoundException();
        }
    }

    private Sessao dtoToEntity(SessaoDto sessaoDto){
        Sessao sessao = new Sessao();
        BeanUtils.copyProperties(sessaoDto, sessao);
        sessao.setPauta(pautaRepository.findById(sessaoDto.getPautaId()).get());
        return sessao;
    }

}
