package br.com.assistecnologia.GestaoDeLogisitica.Obra.service;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.domain.Obra;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/obras")
public class ObraController {

    @Autowired
    private ObraRepository obraRepository;


    @GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Obra> listarObras(){

        return obraRepository.findAll();
    }
    @GetMapping(path = "/{idObra}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Obra listarObras(@PathVariable(name = "idObra")Long idObra){

        return obraRepository.findById(idObra)
                .orElseThrow(() -> new ResourceNotFoundException("Obra " + idObra + " not found"));
    }

    @PostMapping(path = "/")
    public Obra cadastrarObra(@RequestBody Obra obra ){

       return obraRepository.save(obra);
    }

    @PutMapping(path = "/{id}")
    public Obra editarObra(@PathVariable Long id, @RequestBody Obra obraToupdate ){
        return obraRepository.findById(id).map(obra -> {
            obra.setCodigo(obraToupdate.getCodigo());
            obra.setDescricao(obraToupdate.getDescricao());
            obra.setNome(obraToupdate.getNome());
            return obraRepository.save(obra);
        }).orElseThrow(() -> new ResourceNotFoundException("Obra " + id + " not found"));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id)
    {
        return obraRepository.findById(id).map(obra -> {
            obraRepository.delete(obra);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Obra " + id + " not found"));
    }

}


