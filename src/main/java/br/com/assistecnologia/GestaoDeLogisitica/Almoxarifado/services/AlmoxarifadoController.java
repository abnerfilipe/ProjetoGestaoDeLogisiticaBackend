package br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.services;

import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.domain.Almoxarifado;
import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.repository.AlmoxarifadoRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/")
public class AlmoxarifadoController {

    @Autowired
    private AlmoxarifadoRepository almoxarifadoRepository;

    @Autowired
    private ObraRepository obraRepository;

    @GetMapping(path = "/almoxarifados/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Almoxarifado> listar(){

        return almoxarifadoRepository.findAll();
    }
    @GetMapping(path = "/almoxarifados/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Almoxarifado mostrar(@PathVariable Long id){

        return almoxarifadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almoxarifado " + id + "not found"));
    }

    @PostMapping(path = "/obras/{idObra}/almoxarifados")
    public Almoxarifado criar(@PathVariable Long idObra,@RequestBody Almoxarifado almoxarifado ){
        return obraRepository.findById(idObra).map(obra -> {
            almoxarifado.setObra(obra);
            return almoxarifadoRepository.save(almoxarifado);
        }).orElseThrow(() -> new ResourceNotFoundException("Obra " + idObra + " not found"));
    }

    @PutMapping("/obras/{idObra}/almoxarifados/{idAlmoxarifado}")
    public Almoxarifado updatePost(@PathVariable Long idObra,
                                   @PathVariable Long idAlmoxarifado,
                                   @RequestBody Almoxarifado almoxarifadoRequest) {
        if(!obraRepository.existsById(idObra)) {
            throw new ResourceNotFoundException("Obra " + idObra + " not found");
        }

        return almoxarifadoRepository.findById(idAlmoxarifado).map(almoxarifado -> {
            almoxarifado.setNome(almoxarifadoRequest.getNome());
            return almoxarifadoRepository.save(almoxarifado);
        }).orElseThrow(() -> new ResourceNotFoundException("Almoxarifado " + idAlmoxarifado + "not found"));

    }


    @DeleteMapping("/almoxarifados/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return almoxarifadoRepository.findById(id).map(almoxarifado -> {
            almoxarifadoRepository.delete(almoxarifado);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Almoxarifado " + id + " not found"));
    }


}


