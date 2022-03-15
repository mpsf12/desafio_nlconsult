package com.nt.consult.desafio.service.impl;

import com.nt.consult.desafio.exception.SessaoAlreadyCreatedException;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.service.SessaoService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class SessaoServiceImpl implements SessaoService {

    private static final Logger log = LoggerFactory.getLogger(SessaoServiceImpl.class);

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    public List<Sessao> findAllSessoes() {
        return (List<Sessao>) sessaoRepository.findAll();
    }

    public Sessao saveSessao(Sessao sessao) {
        return sessaoRepository.save(sessao);
    }

    public ResponseEntity<Sessao> abrirSessaoVotacao(long pautaId) {
        return abrirSessaoVotacao(pautaId, 60000);
    }

    public ResponseEntity<Sessao> abrirSessaoVotacao(long pautaId, long duracao){
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);

        if(pauta.isPresent()) {
            Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
            if(sessao.isPresent()) {
                throw new SessaoAlreadyCreatedException("Sessão de votação já criada para a pauta de id " + pautaId);
            }
            Sessao newSessao = new Sessao(System.currentTimeMillis(), duracao, pauta.get());
            sessaoRepository.save(newSessao);
            return ResponseEntity.ok().body(newSessao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
