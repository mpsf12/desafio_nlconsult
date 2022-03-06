package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.exception.PautaNotFoundException;
import com.nt.consult.desafio.exception.SessaoEncerradaException;
import com.nt.consult.desafio.exception.SessaoNotFoundException;
import com.nt.consult.desafio.exception.UserNotFoundException;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.model.User;
import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.repository.UserRepository;
import com.nt.consult.desafio.repository.VotacaoRepository;
import com.nt.consult.desafio.util.VotacaoEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    		@PathVariable(value = "voto") VotacaoEnum voto){
    	Optional<User> user = userRepository.findById(user_id);
    	Optional<Pauta> pauta = pautaRepository.findById(pauta_id); 
    	
    	if(user.isPresent() && pauta.isPresent()) {
    		Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
    		if(!sessao.isEmpty()) {
    			if(!sessao.get().sessaoFinalizada()) {
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
