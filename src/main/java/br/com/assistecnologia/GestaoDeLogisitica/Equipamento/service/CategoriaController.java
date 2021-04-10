package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.service;


import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Categoria;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Medicao;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.CategoriaRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/equipamentos")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MedicaoRepository medicaoRepository;

    @GetMapping(path = "/categorias/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @GetMapping(path = "/categorias/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Categoria mostrar(@PathVariable(name = "id") Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria " + id + " not found"));
    }

    @PostMapping(path = "/medicoes/{idMedicao}/categorias")
    public Categoria criar(@PathVariable (value = "idMedicao") Long idMedicao,
                           @RequestBody Categoria categoria) {
        return medicaoRepository.findById(idMedicao).map(medicao -> {
            categoria.setMedicao(medicao);
            medicao.setCategoria(categoria);
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new ResourceNotFoundException("Medicao " + idMedicao + " not found"));
    }

    @PutMapping(path = "/medicoes/{idMedicao}/categorias/{idCategoria}")
    public Categoria editar(@PathVariable (value = "idMedicao") Long idMedicao,
                       @PathVariable (value = "idCategoria") Long idCategoria,
                       @RequestBody Categoria categoriaRequest) {

        Medicao medicao = medicaoRepository.findById(idMedicao)
                .orElseThrow(() -> new ResourceNotFoundException("Medicao " + idMedicao + " not found"));

        return categoriaRepository.findById(idCategoria).map(categoria -> {
            categoria.setNome(categoriaRequest.getNome());
            categoria.setMedicao(medicao);
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new ResourceNotFoundException("Categoria " + idCategoria + "not found"));
    }

    @DeleteMapping(value = "/medicoes/{idMedicao}/categorias/{idCategoria}")
    public ResponseEntity<?> deletar(@PathVariable (value = "idMedicao") Long idMedicao,
                 @PathVariable (value = "idCategoria") Long idCategoria) {
        return categoriaRepository.findByIdAndMedicaoId(idCategoria, idMedicao).map(categoria -> {
            categoriaRepository.delete(categoria);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Categoria not found with id " + idCategoria + " and medicao " + idMedicao));
    }

}


