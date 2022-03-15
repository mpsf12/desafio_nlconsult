package com.nt.consult.desafio.controller;

import java.util.*;

import com.nt.consult.desafio.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nt.consult.desafio.model.Sessao;

@RestController
@RequestMapping("/api/sessao")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;

	@GetMapping
	public List<Sessao> findAllSessoes() {
		return sessaoService.findAllSessoes();
	}
	
	@GetMapping("/{pauta_id}")
    public ResponseEntity<Sessao> abrirSessaoVotacao(@PathVariable(value = "pauta_id") long pauta_id) {
    	return sessaoService.abrirSessaoVotacao(pauta_id);
    }
	
	@GetMapping("/{pauta_id}/{duracao}")
	public ResponseEntity<Sessao> abrirSessaoVotacao(@PathVariable(value = "pauta_id") long pauta_id,@PathVariable(value = "duracao")  long duracao){
		return sessaoService.abrirSessaoVotacao(pauta_id,duracao);
	}
 
    @PostMapping
    public Sessao saveSessao(@Validated @RequestBody Sessao sessao) {
    	return sessaoService.saveSessao(sessao);
    }
}
