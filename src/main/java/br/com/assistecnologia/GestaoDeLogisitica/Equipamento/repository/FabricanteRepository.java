package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Fabricante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricanteRepository extends CrudRepository<Fabricante, Long> {
}
