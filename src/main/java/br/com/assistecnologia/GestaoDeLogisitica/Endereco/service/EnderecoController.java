package br.com.assistecnologia.GestaoDeLogisitica.Endereco.service;


import br.com.assistecnologia.GestaoDeLogisitica.Endereco.domain.Endereco;
import br.com.assistecnologia.GestaoDeLogisitica.Endereco.repository.EnderecoRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;


    @GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Endereco> listar(){

        return enderecoRepository.findAll();
    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Endereco listar(@PathVariable Long id){

        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereco " + id + " not found"));
    }

    @PostMapping(path = "/")
    public Endereco criar(@RequestBody Endereco endereco){

        return enderecoRepository.save(endereco);
    }
    @PutMapping("/{id}")
    public Endereco updatePost(@PathVariable Long id,  @RequestBody Endereco enderecoRequest) {
        return enderecoRepository.findById(id).map(endereco -> {
            endereco.setBairro(enderecoRequest.getBairro());
            endereco.setCidade(enderecoRequest.getCidade());
            endereco.setComplemento(enderecoRequest.getComplemento());
            endereco.setEstado(enderecoRequest.getEstado());
            endereco.setLogradouro(enderecoRequest.getLogradouro());
            endereco.setNumero(enderecoRequest.getNumero());
            return enderecoRepository.save(endereco);
        }).orElseThrow(() -> new ResourceNotFoundException("Endereco " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return enderecoRepository.findById(id).map(endereco -> {
            enderecoRepository.delete(endereco);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Endereco " + id + " not found"));
    }

}


