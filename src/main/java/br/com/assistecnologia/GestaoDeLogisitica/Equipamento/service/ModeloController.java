package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.service;


import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Fabricante;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Modelo;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.FabricanteRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/equipamentos/")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @GetMapping(path = "/modelos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Modelo> listar() {

        return modeloRepository.findAll();
    }

    @GetMapping(path = "/modelos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Modelo mostrar(@PathVariable(name = "id") Long id) {

        return modeloRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Modelo " + id + " not found"));
    }

    @PostMapping(path = "/fabricantes/{idFabricante}/modelos")
    public Modelo criar(@PathVariable(value = "idFabricante") Long idFabricante, @RequestBody Modelo modelo) {
        return fabricanteRepository.findById(idFabricante).map(fabricante -> {
            modelo.setFabricante(fabricante);
            return modeloRepository.save(modelo);
        }).orElseThrow(() -> new ResourceNotFoundException("Fabricante " + idFabricante + " not found"));
    }

    @PutMapping(path = "/fabricantes/{idFabricante}/modelos/{idModelo}")
    public Modelo editar(@PathVariable(value = "idFabricante") Long idFabricante,
                         @PathVariable(value = "idModelo")Long idModelo,
                         @RequestBody Modelo modeloRequest) {
            Fabricante fabricante = fabricanteRepository.findById(idFabricante)
                    .orElseThrow(()->new ResourceNotFoundException("Fabricante " + idFabricante + " not found"));
            return modeloRepository.findById(idModelo).map(modelo -> {
                modelo.setNome(modeloRequest.getNome());
                modelo.setFabricante(fabricante);
                return modeloRepository.save(modelo);
            }).orElseThrow(() -> new ResourceNotFoundException("Modelo " + idModelo + " not found"));
    }

    @DeleteMapping("/fabricantes/{idFabricante}/modelos/{idModelo}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "idFabricante") Long idFabricante,
                                           @PathVariable(value = "idModelo")Long idModelo) {
        return modeloRepository.findByIdAndFabricanteId(idModelo, idFabricante).map(modelo -> {
            modeloRepository.delete(modelo);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Modelo not found with id " + idModelo + " and fabricante " + idFabricante));
    }
}


