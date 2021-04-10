package br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.services;

import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.domain.Almoxarifado;
import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.domain.Material;
import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.repository.AlmoxarifadoRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Almoxarifado.repository.MaterialRepository;
import br.com.assistecnologia.GestaoDeLogisitica.Equipamento.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/")
public class MaterialController {

    @Autowired
    private AlmoxarifadoRepository almoxarifadoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping(path = "/almoxarifados/{idAlmoxarifado}/material",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Material> listar(@PathVariable Long idAlmoxarifado){

        return materialRepository.findAllMaterialByAlmoxarifadoId(idAlmoxarifado);
    }
    @GetMapping(path = "/almoxarifados/{idAlmoxarifado}/material/{idMaterial}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Material mostrar(@PathVariable Long idAlmoxarifado, @PathVariable Long idMaterial){
        if(!almoxarifadoRepository.existsById(idAlmoxarifado)) {
            throw new ResourceNotFoundException("Almoxarifado " + idAlmoxarifado + " not found");
        }
        return materialRepository.findByIdAndAlmoxarifadoId(idMaterial,idAlmoxarifado)
                .orElseThrow(() -> new ResourceNotFoundException("Material " + idMaterial + " not found"));
    }

    @PostMapping(path = "/almoxarifados/{idAlmoxarifado}/material/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Almoxarifado criar(@PathVariable Long idAlmoxarifado, @RequestBody Material material){

        return almoxarifadoRepository.findById(idAlmoxarifado).map(almoxarifado -> {
            almoxarifado.setMaterial(material);
            material.setAlmoxarifado(almoxarifado);
            return almoxarifadoRepository.save(almoxarifado);
        }).orElseThrow(() -> new ResourceNotFoundException("Almoxarifado " + idAlmoxarifado + " not found"));
    }

    @PutMapping(path = "/almoxarifados/{idAlmoxarifado}/material/{idMaterial}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Material editar(@PathVariable Long idAlmoxarifado, @PathVariable Long idMaterial, @RequestBody Material materialRequest){
        if(!almoxarifadoRepository.existsById(idAlmoxarifado)) {
            throw new ResourceNotFoundException("Almoxarifado " + idAlmoxarifado + " not found");
        }
        return materialRepository.findById(idMaterial).map(material -> {
            material.setNome(materialRequest.getNome());
            material.setDescricao(materialRequest.getDescricao());
            material.setObservacao(materialRequest.getObservacao());
            material.setPeso(materialRequest.getPeso());
            return materialRepository.save(material);
        }).orElseThrow(() -> new ResourceNotFoundException("Material " + idMaterial + " not found"));
    }


    @DeleteMapping("/almoxarifados/{idAlmoxarifado}/material/{idMaterial}")
    public ResponseEntity<?> delete(@PathVariable Long idMaterial, @PathVariable Long idAlmoxarifado) {
        if(!almoxarifadoRepository.existsById(idAlmoxarifado)) {
            throw new ResourceNotFoundException("Almoxarifado " + idAlmoxarifado + " not found");
        }
        return materialRepository.findByIdAndAlmoxarifadoId(idMaterial,idAlmoxarifado).map(material -> {
            materialRepository.delete(material);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Material " + idMaterial + " not found"));
    }


}


