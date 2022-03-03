package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.repository.VotacaoRepository;
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
    private VotacaoRepository VotacaoRepository;
        
    @GetMapping
    public List<Votacao> findAllVotacaos() {
    	return (List<Votacao>) VotacaoRepository.findAll();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Votacao> findVotacaoById(@PathVariable(value = "id") long id) {
    	Optional<Votacao> Votacao = VotacaoRepository.findById(id);
    	 
        if(Votacao.isPresent()) {
            return ResponseEntity.ok().body(Votacao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @PostMapping
    public Votacao saveVotacao(@Validated @RequestBody Votacao Votacao) {
    	return VotacaoRepository.save(Votacao);
    }
}
