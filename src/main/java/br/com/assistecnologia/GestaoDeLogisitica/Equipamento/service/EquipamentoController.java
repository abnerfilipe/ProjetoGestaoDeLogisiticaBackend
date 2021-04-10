package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.service;


import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Categoria;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Equipamento;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Modelo;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.CategoriaRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.EquipamentoRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.ModeloRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.domain.Obra;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @GetMapping(path = "/equipamentos",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Equipamento> mostrar(){
        return equipamentoRepository.findAll();
    }
    @GetMapping(path = "/obras/{idObra}/equipamentos",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Equipamento> mostrar(@PathVariable Long idObra){
        if(!obraRepository.existsById(idObra)) {
            throw new ResourceNotFoundException("Obra " + idObra + " not found");
        }
        return obraRepository.findById(idObra).get().getEquipamentos();
    }

    @GetMapping(path = "/obras/{idObra}/equipamentos/{idEquipamento}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Equipamento> listar(@PathVariable Long idEquipamento, @PathVariable Long idObra){

        return equipamentoRepository.findByIdAndObraId(idEquipamento,idObra);
    }


    @PostMapping(path = "/obras/{idObra}/categoria/{idCategoria}/modelo/{idModelo}/equipamentos")
    public Equipamento criar(@PathVariable Long idObra,
                             @PathVariable Long idCategoria,
                             @PathVariable Long idModelo,
                             @RequestBody Equipamento equipamento){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria " + idCategoria + " not found"));
        Modelo modelo = modeloRepository.findById(idModelo)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo " + idModelo + " not found"));
        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new ResourceNotFoundException("Obra " + idObra + " not found"));

        equipamento.setCategoria(categoria);
        categoria.setEquipamento(equipamento);
        equipamento.setModelo(modelo);
        modelo.setEquipamento(equipamento);
        equipamento.setObra(obra);
        obra.setEquipamento(equipamento);
        return equipamentoRepository.save(equipamento);
    }

    @PutMapping(path = "/obras/{idObra}/equipamentos/{idEquipamento}/categoria/{idCategoria}/modelo/{idModelo}")
    public Equipamento editar(@PathVariable Long idObra,
                             @PathVariable Long idCategoria,
                             @PathVariable Long idModelo,
                             @PathVariable Long idEquipamento,
                             @RequestBody Equipamento equipamentoRequest
    ){

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria " + idCategoria + " not found"));
        Modelo modelo = modeloRepository.findById(idModelo)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo " + idModelo + " not found"));
        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new ResourceNotFoundException("Obra " + idObra + " not found"));

        return equipamentoRepository.findById(idEquipamento).map(equipamento -> {
            equipamento.setNome(equipamentoRequest.getNome());
            equipamento.setIdentificacao(equipamentoRequest.getIdentificacao());
            equipamento.setStatus(equipamentoRequest.getStatus());
            equipamento.setTanque(equipamentoRequest.getTanque());
            equipamento.setTipo_combustivel(equipamentoRequest.getTipo_combustivel());
            equipamento.setStatus(equipamentoRequest.getStatus());
            equipamento.setCategoria(categoria);
            equipamento.setModelo(modelo);
            equipamento.setObra(obra);
            return equipamentoRepository.save(equipamento);
        }).orElseThrow(() -> new ResourceNotFoundException("Equipamento " + idEquipamento + "not found"));

    }

    @DeleteMapping(path = "/obras/{idObra}/equipamentos/{idEquipamento}")
    public ResponseEntity<?> deletar(@PathVariable Long idEquipamento, @PathVariable Long idObra){

        return equipamentoRepository.findByIdAndObraId(idEquipamento, idObra).map(equipamento -> {
            equipamentoRepository.delete(equipamento);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Equipamento not found with id " + idEquipamento + " and obra " + idObra));
    }

}


