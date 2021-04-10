package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Modelo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeloRepository extends CrudRepository<Modelo, Long> {
    Optional<Modelo> findByIdAndFabricanteId(Long id, Long fabricante_id);
}
