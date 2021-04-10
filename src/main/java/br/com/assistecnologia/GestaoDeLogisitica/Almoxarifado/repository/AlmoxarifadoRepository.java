package br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.domain.Almoxarifado;
import br.com.assistecnologia.GestaoDeLogisitica.Endereco.domain.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface AlmoxarifadoRepository extends CrudRepository<Almoxarifado, Long> {
}
