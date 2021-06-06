package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.service;


import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Fabricante;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/equipamentos/fabricantes")
public class FabricanteController {

    @Autowired
    private FabricanteRepository fabricanteRepository;



    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Fabricante> listar(){

        return fabricanteRepository.findAll();
    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Fabricante> mostrar(@PathVariable(name = "id")Long id){
        if(!fabricanteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fabricante " + id + " not found");
        }
        return fabricanteRepository.findById(id);
    }

    @PostMapping(path = "")
    public Fabricante criar(@RequestBody Fabricante fabricante){

        return fabricanteRepository.save(fabricante);
    }

    @PutMapping(path = "/{id}")
    public Fabricante editar(@PathVariable Long id, @RequestBody Fabricante fabricanteRequest ){
        return fabricanteRepository.findById(id).map(fabricante -> {
            fabricante.setNome(fabricanteRequest.getNome());
            return fabricanteRepository.save(fabricante);
        }).orElseThrow(() -> new ResourceNotFoundException("Fabricante " + id + " not found"));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") long id){

        return fabricanteRepository.findById(id).map(fabricante -> {
            fabricanteRepository.delete(fabricante);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Fabricante not found with id " + id));
    }

}


