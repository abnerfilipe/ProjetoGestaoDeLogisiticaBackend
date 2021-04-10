package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain.Medicao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicaoRepository extends CrudRepository<Medicao, Long> {
}
