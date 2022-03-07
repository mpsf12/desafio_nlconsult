package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.exception.*;
import com.nt.consult.desafio.model.*;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.repository.UserRepository;
import com.nt.consult.desafio.repository.VotacaoRepository;
import com.nt.consult.desafio.util.HttpRequest;
import com.nt.consult.desafio.util.UserPodeVotarEnum;
import com.nt.consult.desafio.util.VotacaoEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/votacao")
public class VotacaoController {

	@Autowired
    private VotacaoRepository votacaoRepository;
	@Autowired
	private UserRepository userRepository; 
	@Autowired
	private PautaRepository pautaRepository;
	@Autowired
	private SessaoRepository sessaoRepository;

	private String urlPermissaoVoto = "https://user-info.herokuapp.com/users/";
        
    @GetMapping
    public List<Votacao> findAllVotacaos() {
    	return (List<Votacao>) votacaoRepository.findAll();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Votacao> findVotacaoById(@PathVariable(value = "id") long id) {
    	Optional<Votacao> Votacao = votacaoRepository.findById(id);
    	 
        if(Votacao.isPresent()) {
            return ResponseEntity.ok().body(Votacao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{user_id}/{pauta_id}/{voto}")
    public ResponseEntity<Votacao> votarPauta(@PathVariable(value = "user_id") long user_id, 
    		@PathVariable(value = "pauta_id") long pauta_id, 
    		@PathVariable(value = "voto") VotacaoEnum voto) throws Exception {
    	Optional<User> user = userRepository.findById(user_id);
    	Optional<Pauta> pauta = pautaRepository.findById(pauta_id); 
    	
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
    				return ResponseEntity.ok().body(votacao);
    			} else {
    				throw new SessaoEncerradaException("Sessão já concluída.");
    			}
    		} else {
    			throw new SessaoNotFoundException("Não foi possível encontrar sessão para a pauta de id " + pauta_id);
    		}
    	} else {
    		if(user.isEmpty()) {
				throw new UserNotFoundException("Não foi possível encontrar o usuário de id " + user_id);
			} else {
				throw new PautaNotFoundException("Não foi possível encontrar a pauta de id " + pauta_id);
			}
    	}
    }
 
    @PostMapping
    public Votacao saveVotacao(@Validated @RequestBody Votacao Votacao) {
    	return votacaoRepository.save(Votacao);
    }
}
