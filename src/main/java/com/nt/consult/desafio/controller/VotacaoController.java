package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.dto.VotacaoDto;
import com.nt.consult.desafio.service.VotacaoService;
import com.nt.consult.desafio.enums.VotacaoEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votacao")
public class VotacaoController {

	@Autowired
    private VotacaoService votacaoService;

    @GetMapping
    public List<VotacaoDto> findAllVotacaos() {
    	return votacaoService.findAllVotacaos();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<VotacaoDto> findVotacaoById(@PathVariable(value = "id") long id) throws Exception {
		return ResponseEntity.ok().body(votacaoService.findVotacaoById(id));
    }
    
    @GetMapping("/{user_id}/{pauta_id}/{voto}")
    public ResponseEntity<VotacaoDto> votarPauta(@PathVariable(value = "user_id") long user_id,
    		@PathVariable(value = "pauta_id") long pauta_id, 
    		@PathVariable(value = "voto") VotacaoEnum voto) throws Exception {
		return ResponseEntity.ok().body(votacaoService.votarPauta(user_id,pauta_id,voto));
    }
 
    @PostMapping
    public VotacaoDto saveVotacao(@Validated @RequestBody VotacaoDto votacaoDto) {
    	return votacaoService.saveVotacao(votacaoDto);
    }
}
