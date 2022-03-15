package com.nt.consult.desafio.controller;

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
    private VotacaoService votacaoService;

    @GetMapping
    public List<Votacao> findAllVotacaos() {
    	return (List<Votacao>) votacaoService.findAllVotacaos();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Votacao> findVotacaoById(@PathVariable(value = "id") long id) throws Exception {
		return ResponseEntity.ok().body(votacaoService.findVotacaoById(id));
    }
    
    @GetMapping("/{user_id}/{pauta_id}/{voto}")
    public ResponseEntity<Votacao> votarPauta(@PathVariable(value = "user_id") long user_id, 
    		@PathVariable(value = "pauta_id") long pauta_id, 
    		@PathVariable(value = "voto") VotacaoEnum voto) throws Exception {
		return ResponseEntity.ok().body(votacaoService.votarPauta(user_id,pauta_id,voto));
    }
 
    @PostMapping
    public Votacao saveVotacao(@Validated @RequestBody Votacao Votacao) {
    	return votacaoService.saveVotacao(Votacao);
    }
}
