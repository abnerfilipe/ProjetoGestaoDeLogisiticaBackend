package br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.repository;

import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.domain.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MaterialRepository extends CrudRepository<Material, Long> {
    Iterable<Material> findAllMaterialByAlmoxarifadoId(Long almoxarifado_id);
    Optional<Material> findByIdAndAlmoxarifadoId(Long id, Long almoxarifado_id);
}
