package br.com.assistecnologia.GestaoDeLogisitica.Funcionario.service;


import br.com.assistecnologia.GestaoDeLogisitica.Endereco.domain.Endereco;
import br.com.assistecnologia.GestaoDeLogisitica.Endereco.repository.EnderecoRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import br.com.assistecnologia.GestaoDeLogisitica.Funcionario.domain.Funcionario;
import br.com.assistecnologia.GestaoDeLogisitica.Funcionario.repository.FuncionarioRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.domain.Obra;
import br.com.assistecnologia.GestaoDeLogisitica.Obra.repository.ObraRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Usuario.domain.Usuario;
import br.com.assistecnologia.GestaoDeLogisitica.Usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping(path = "/funcionarios",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Funcionario> listar(){

        return funcionarioRepository.findAll();
    }
    @GetMapping(path = "/funcionarios/{idFuncionario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario mostrar(@PathVariable(name = "idFuncionario") long idFuncionario){

        return funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario " + idFuncionario + " not found"));
    }

    @PostMapping(path = "/funcionarios/obra/{idObra}/endereco/{idEndereco}/usuario/{idUsuario}")
    public Funcionario criar(@RequestBody Funcionario funcionario,
                      @PathVariable Long idObra,
                      @PathVariable Long idUsuario,
                      @PathVariable Long idEndereco){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario " + idUsuario + " not found"));
        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new ResourceNotFoundException("Obra " + idObra + " not found"));
        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("Endereco " + idEndereco + " not found"));

        funcionario.setObra(obra);
        funcionario.setUsuario(usuario);
        funcionario.setEndereco(endereco);
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping(path = "/funcionarios/{idFuncionario}/obra/{idObra}/endereco/{idEndereco}")
    public Funcionario editar(@PathVariable Long idFuncionario,
                              @PathVariable Long idObra,
                              @PathVariable Long idEndereco,
                              @RequestBody Funcionario funcionarioRequest){
        Obra obra = obraRepository.findById(idObra)
                .orElseThrow(() -> new ResourceNotFoundException("Obra " + idObra + " not found"));
        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("Endereco " + idEndereco + " not found"));
        return funcionarioRepository.findById(idFuncionario).map(funcionario -> {
            funcionario.setNome(funcionarioRequest.getNome());
            funcionario.setDataNascimento(funcionarioRequest.getDataNascimento());
            funcionario.setCpf(funcionarioRequest.getCpf());
            funcionario.setSexo(funcionarioRequest.getSexo());
            funcionario.setMatricula(funcionarioRequest.getMatricula());
            funcionario.setCargo(funcionarioRequest.getCargo());
            funcionario.setObra(obra);
            funcionario.setEndereco(endereco);
            return funcionarioRepository.save(funcionario);
        }).orElseThrow(() -> new ResourceNotFoundException("Funcionario " + idFuncionario + "not found"));
    }


    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return funcionarioRepository.findById(id).map(funcionario -> {
            funcionarioRepository.delete(funcionario);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Funcionario " + id + " not found"));
    }
}


