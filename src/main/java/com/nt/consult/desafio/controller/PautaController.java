package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.dto.PautaDto;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.service.PautaService;
import com.nt.consult.desafio.enums.ResultadoPautaEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@GetMapping
	public List<PautaDto> findAllPautas() {
		return pautaService.findAllPautas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PautaDto> findPautaById(@PathVariable(value = "id") long id) {
		return ResponseEntity.ok().body(pautaService.findPautaById(id));
	}

	@GetMapping("/contabilizar/{pautaId}")
	public ResultadoPautaEnum contabilizarVotacaoPauta(@PathVariable(value = "pautaId") long pautaId) {
		return pautaService.contabilizarVotacaoPauta(pautaId);

	}

	@PostMapping
	public PautaDto savePauta(@Validated @RequestBody PautaDto pautaDto) {
		return pautaService.savePauta(pautaDto);
	}
}
