package com.nt.consult.desafio.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nt.consult.desafio.exception.SessaoAlreadyCreatedException;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;

@RestController
@RequestMapping("/api/sessao")
public class SessaoController {
	
	@Autowired
	private SessaoRepository sessaoRepository;
	@Autowired
	private PautaRepository pautaRepository;

	@GetMapping
	public List<Sessao> findAllSessoes() {
		return (List<Sessao>) sessaoRepository.findAll();
	}
	
	@GetMapping("/{pauta_id}")
    public ResponseEntity<Sessao> abrirSessaoVotacao(@PathVariable(value = "pauta_id") long pauta_id) {
    	return abrirSessaoVotacao(pauta_id, 60000);
    }
	
	@GetMapping("/{pauta_id}/{duracao}")
	public ResponseEntity<Sessao> abrirSessaoVotacao(@PathVariable(value = "pauta_id") long pauta_id,@PathVariable(value = "duracao")  long duracao){
		Optional<Pauta> pauta = pautaRepository.findById(pauta_id);
		
		if(pauta.isPresent()) {
			Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
			if(sessao.isPresent()) {
				throw new SessaoAlreadyCreatedException("Sessão de votação já criada para a pauta de id " + pauta_id);
			}
			Sessao newSessao = new Sessao(System.currentTimeMillis(), duracao, pauta.get());
			sessaoRepository.save(newSessao);
			return ResponseEntity.ok().body(newSessao);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
 
    @PostMapping
    public Sessao saveSessao(@Validated @RequestBody Sessao sessao) {
    	return sessaoRepository.save(sessao);
    }
}
