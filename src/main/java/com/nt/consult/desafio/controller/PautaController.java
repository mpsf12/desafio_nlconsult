package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.exception.*;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.repository.PautaRepository;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.repository.VotacaoRepository;
import com.nt.consult.desafio.util.ResultadoPautaEnum;
import com.nt.consult.desafio.util.VotacaoEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pauta")
public class PautaController {

	@Autowired
	private PautaRepository pautaRepository;
	@Autowired
	private VotacaoRepository votacaoRepository;
	@Autowired
	private SessaoRepository sessaoRepository;

	@GetMapping
	public List<Pauta> findAllPautas() {
		return (List<Pauta>) pautaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pauta> findPautaById(@PathVariable(value = "id") long id) {
		Optional<Pauta> Pauta = pautaRepository.findById(id);

		if (Pauta.isPresent()) {
			return ResponseEntity.ok().body(Pauta.get());
		} else {
			throw new PautaNotFoundException("Não foi possível encontrar a pauta de id " + id);
		}
	}

	@GetMapping("/contabilizar/{id}")
	public ResultadoPautaEnum contabilizarVotacaoPauta(@PathVariable(value = "id") long id) {
		Optional<Pauta> pauta = pautaRepository.findById(id);

		if (pauta.isPresent()) {
			Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
			if (sessao.isPresent()) {
				if (!sessao.get().sessaoFinalizada()) {
					throw new SessaoNaoFinalizadaException(
							"Sessao de votação da Pauta de id " + id + " ainda nao foi finalizada.");
				}
			} else {
				throw new SessaoNotFoundException("Não foi possivel encontrar a sessão da Pauta de id " + id );
			}
			List<Votacao> votacao = votacaoRepository.findByPauta(pauta.get());
			int sim = 0, nao = 0;
			for (Votacao voto : votacao) {
				if (voto.getVoto() == VotacaoEnum.Sim) {
					sim++;
				} else {
					nao++;
				}
			}
			// Julguei que a pauta so sera aprovada se houver a maioria dos votos a favor,
			// ou seja, o empate resultada em ela sendo reprovada
			if (sim > nao) {
				return ResultadoPautaEnum.Aprovado;
			} else {
				return ResultadoPautaEnum.Reprovado;
			}
		} else {
			throw new PautaNotFoundException("Não foi possível encontrar a pauta de id " + id);
		}

	}

	@PostMapping
	public Pauta savePauta(@Validated @RequestBody Pauta pauta) {
		return pautaRepository.save(pauta);
	}
}
