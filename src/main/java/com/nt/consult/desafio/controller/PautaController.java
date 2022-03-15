package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.service.PautaService;
import com.nt.consult.desafio.util.ResultadoPautaEnum;

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
	public List<Pauta> findAllPautas() {
		return pautaService.findAllPautas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pauta> findPautaById(@PathVariable(value = "id") long id) {
		return ResponseEntity.ok().body(pautaService.findPautaById(id));
	}

	@GetMapping("/contabilizar/{pautaId}")
	public ResultadoPautaEnum contabilizarVotacaoPauta(@PathVariable(value = "pautaId") long pautaId) {
		return pautaService.contabilizarVotacaoPauta(pautaId);

	}

	@PostMapping
	public Pauta savePauta(@Validated @RequestBody Pauta pauta) {
		return pautaService.savePauta(pauta);
	}
}
