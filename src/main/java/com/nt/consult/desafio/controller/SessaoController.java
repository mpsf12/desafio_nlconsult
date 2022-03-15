package com.nt.consult.desafio.controller;

import java.util.*;

import com.nt.consult.desafio.dto.SessaoDto;
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
	public List<SessaoDto> findAllSessoes() {
		return sessaoService.findAllSessoes();
	}
	
	@GetMapping("/{pauta_id}")
    public ResponseEntity<SessaoDto> abrirSessaoVotacao(@PathVariable(value = "pauta_id") long pauta_id) {
    	return ResponseEntity.ok().body(sessaoService.abrirSessaoVotacao(pauta_id));
    }
	
	@GetMapping("/{pauta_id}/{duracao}")
	public ResponseEntity<SessaoDto> abrirSessaoVotacao(@PathVariable(value = "pauta_id") long pauta_id,@PathVariable(value = "duracao")  long duracao){
		return ResponseEntity.ok().body(sessaoService.abrirSessaoVotacao(pauta_id,duracao));
	}
 
    @PostMapping
    public SessaoDto saveSessao(@Validated @RequestBody SessaoDto sessaoDto) {
    	return sessaoService.saveSessao(sessaoDto);
    }
}
