package br.com.assistecnologia.GestaoDeLogisitica.Funcionario.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Funcionario.domain.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

}
