package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.service;


import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Medicao;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/equipamentos/medicoes")
public class MedicaoController {

    @Autowired
    private MedicaoRepository medicaoRepository;



    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Medicao> listar(){

        return medicaoRepository.findAll();
    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Medicao mostrar(@PathVariable(name = "id")Long id){
       return  medicaoRepository.findById(id)
               .orElseThrow(()->  new ResourceNotFoundException("Medicao " + id + " not found"));
    }

    @PostMapping(path = "")
    public Medicao criar(@RequestBody Medicao medicao){
        return medicaoRepository.save(medicao);
    }

    @PutMapping(path = "/{id}")
    public Medicao editar(@PathVariable Long id, @RequestBody Medicao medicaoRequest ){
        return medicaoRepository.findById(id).map(medicao -> {
            medicao.setNome(medicaoRequest.getNome());
            medicao.setUnidade(medicaoRequest.getUnidade());
            return medicaoRepository.save(medicao);
        }).orElseThrow(() -> new ResourceNotFoundException("Medicao " + id + " not found"));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") long id){
        return  medicaoRepository.findById(id).map(medicao -> {
            medicaoRepository.delete(medicao);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->  new ResourceNotFoundException("Medicao " + id + " not found"));
    }

}


