package br.edu.utfpr.apicultura.app.Service;

import br.edu.utfpr.apicultura.app.DTO.HiveDTO;
import br.edu.utfpr.apicultura.app.Model.Device;
import br.edu.utfpr.apicultura.app.Model.Hive;
import br.edu.utfpr.apicultura.app.Model.Property;
import br.edu.utfpr.apicultura.app.Repository.HiveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HiveService {

    private final HiveRepository hiveRepository;

    // Listagem paginada
    public Page<HiveDTO> findAll(Pageable pageable) {
        return hiveRepository.findAll(pageable).map(this::toDTO);
    }

    // Buscar por ID
    public HiveDTO findById(Long id) {
        Hive hive = hiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hive not found with id " + id));
        return toDTO(hive);
    }

    // Criar
    public HiveDTO create(HiveDTO dto) {
        Hive hive = toEntity(dto);
        return toDTO(hiveRepository.save(hive));
    }

    // Atualizar
    public HiveDTO update(Long id, HiveDTO dto) {
        Hive hive = hiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hive not found with id " + id));

        hive.setBeeSpecies(dto.beeSpecies());
        hive.setLastHarvest(dto.lastHarvest());
        hive.setInstallationDate(dto.installationDate());
        hive.setStatus(dto.status());
        hive.setNickname(dto.nickname());
        hive.setPopulation(dto.population());
        hive.setProduction(dto.production());
        hive.setCoordinates(dto.coordinates());
        hive.setInspectionNote(dto.inspectionNote());

        // Apenas setar IDs, você pode expandir para buscar entidades reais se necessário
        if (dto.propertyId() != null) {
            Property p = new Property();
            p.setId(dto.propertyId());
            hive.setProperty(p);
        }
        if (dto.deviceId() != null) {
            Device d = new Device();
            d.setId(dto.deviceId());
            hive.setDevice(d);
        }

        return toDTO(hiveRepository.save(hive));
    }

    // Deletar
    public void delete(Long id) {
        if (!hiveRepository.existsById(id)) {
            throw new EntityNotFoundException("Hive not found with id " + id);
        }
        hiveRepository.deleteById(id);
    }

    // ===== Mapeamento DTO <-> Entity =====
    private HiveDTO toDTO(Hive hive) {
        return new HiveDTO(
                hive.getId(),
                hive.getBeeSpecies(),
                hive.getLastHarvest(),
                hive.getInstallationDate(),
                hive.getStatus(),
                hive.getNickname(),
                hive.getPopulation(),
                hive.getProduction(),
                hive.getCoordinates(),
                hive.getInspectionNote(),
                hive.getProperty() != null ? hive.getProperty().getId() : null,
                hive.getDevice() != null ? hive.getDevice().getId() : null
        );
    }

    private Hive toEntity(HiveDTO dto) {
        Hive hive = new Hive();
        hive.setId(dto.id());
        hive.setBeeSpecies(dto.beeSpecies());
        hive.setLastHarvest(dto.lastHarvest());
        hive.setInstallationDate(dto.installationDate());
        hive.setStatus(dto.status());
        hive.setNickname(dto.nickname());
        hive.setPopulation(dto.population());
        hive.setProduction(dto.production());
        hive.setCoordinates(dto.coordinates());
        hive.setInspectionNote(dto.inspectionNote());

        if (dto.propertyId() != null) {
            Property p = new Property();
            p.setId(dto.propertyId());
            hive.setProperty(p);
        }
        if (dto.deviceId() != null) {
            Device d = new Device();
            d.setId(dto.deviceId());
            hive.setDevice(d);
        }

        return hive;
    }
}
