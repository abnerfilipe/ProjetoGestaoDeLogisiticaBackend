package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    Optional<Categoria> findByIdAndMedicaoId(Long id, Long medicao_id);
}
