package com.nt.consult.desafio.controller;

import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.repository.PautaRepository;
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
    private PautaRepository PautaRepository;
        
    @GetMapping
    public List<Pauta> findAllPautas() {
    	return (List<Pauta>) PautaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> findPautaById(@PathVariable(value = "id") long id) {
    	Optional<Pauta> Pauta = PautaRepository.findById(id);
    	 
        if(Pauta.isPresent()) {
            return ResponseEntity.ok().body(Pauta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @PostMapping
    public Pauta savePauta(@Validated @RequestBody Pauta pauta) {
    	return PautaRepository.save(pauta);
    }
}
