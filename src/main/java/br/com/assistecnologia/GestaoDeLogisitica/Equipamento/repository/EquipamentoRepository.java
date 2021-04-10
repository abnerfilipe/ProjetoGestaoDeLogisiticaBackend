package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Equipamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipamentoRepository extends CrudRepository<Equipamento, Long> {
    Optional<Equipamento> findByIdAndObraId(Long id, Long obra_id);
    Iterable<Equipamento> findAllByObra(Long obra_id);
}
