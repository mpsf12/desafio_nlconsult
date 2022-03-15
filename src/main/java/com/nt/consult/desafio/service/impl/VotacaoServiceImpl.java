package com.nt.consult.desafio.service.impl;

import com.nt.consult.desafio.exception.*;
import com.nt.consult.desafio.model.*;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.repository.UserRepository;
import com.nt.consult.desafio.repository.VotacaoRepository;
import com.nt.consult.desafio.service.VotacaoService;
import com.nt.consult.desafio.util.HttpRequest;
import com.nt.consult.desafio.util.UserPodeVotarEnum;
import com.nt.consult.desafio.util.VotacaoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class VotacaoServiceImpl implements VotacaoService {

    private static final Logger log = LoggerFactory.getLogger(VotacaoServiceImpl.class);

    @Autowired
    private VotacaoRepository votacaoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private SessaoRepository sessaoRepository;

    private String urlPermissaoVoto = "https://user-info.herokuapp.com/users/";

    @Override
    public List<Votacao> findAllVotacaos() {
        return (List<Votacao>) votacaoRepository.findAll();
    }

    @Override
    public Votacao findVotacaoById(long id) throws Exception {
        Optional<Votacao> Votacao = votacaoRepository.findById(id);

        if(Votacao.isPresent()) {
            return Votacao.get();
        } else {
            throw new Exception();
        }
    }

    @Override
    public Votacao votarPauta(long userId, long pautaId, VotacaoEnum voto) throws IOException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);

        if(user.isPresent() && pauta.isPresent()) {
            Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
            if(!sessao.isEmpty()) {
                if(!sessao.get().sessaoFinalizada()) {

                    String url = urlPermissaoVoto + user.get().getCpf();
                    HttpRequest<UserPodeVotar> request = new HttpRequest<>();
                    UserPodeVotar userPodeVotar;
                    try {
                        userPodeVotar = request.get(url, UserPodeVotar.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }
                    if(userPodeVotar != null){
                        if(userPodeVotar.getStatus() == UserPodeVotarEnum.UNABLE_TO_VOTE){
                            throw new UserNaoAutorizadoAVotarException("User de cpf: " + user.get().getCpf() + " não foi autorizado à votar.");
                        }
                    } else {
                        throw new CPFInvalidoException("O CPF " + user.get().getCpf() + " é inválido.");
                    }

                    Votacao votacao = new Votacao(pauta.get(), user.get(), voto);
                    votacaoRepository.save(votacao);
                    return votacao;
                } else {
                    throw new SessaoEncerradaException("Sessão já concluída.");
                }
            } else {
                throw new SessaoNotFoundException("Não foi possível encontrar sessão para a pauta de id " + pautaId);
            }
        } else {
            if(user.isEmpty()) {
                throw new UserNotFoundException("Não foi possível encontrar o usuário de id " + userId);
            } else {
                throw new PautaNotFoundException("Não foi possível encontrar a pauta de id " + pautaId);
            }
        }
    }

    @Override
    public Votacao saveVotacao(Votacao votacao) {
        return votacaoRepository.save(votacao);
    }
}
