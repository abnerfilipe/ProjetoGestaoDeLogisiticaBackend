package br.com.assistecnologia.GestaoDeLogisitica.Usuario.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Usuario.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
