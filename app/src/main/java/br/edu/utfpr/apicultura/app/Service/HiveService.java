package br.edu.utfpr.apicultura.app.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.apicultura.app.DTO.HiveDTO;
import br.edu.utfpr.apicultura.app.Model.Device;
import br.edu.utfpr.apicultura.app.Model.Hive;
import br.edu.utfpr.apicultura.app.Model.Property;
import br.edu.utfpr.apicultura.app.Repository.DeviceRepository;
import br.edu.utfpr.apicultura.app.Repository.HiveRepository;
import br.edu.utfpr.apicultura.app.Repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HiveService {

    private final HiveRepository hiveRepository;
    private final PropertyRepository propertyRepository;
    private final DeviceRepository deviceRepository;

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
    @Transactional
    public HiveDTO create(HiveDTO dto) {
        Hive hive = new Hive();
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
            Property property = propertyRepository.findById(dto.propertyId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Propriedade não encontrada com o ID: " + dto.propertyId()));
            hive.setProperty(property);
        }

        if (dto.deviceId() != null) {
            Device device = deviceRepository.findById(dto.deviceId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Dispositivo não encontrado com o ID: " + dto.deviceId()));
            hive.setDevice(device);
        }

        Hive savedHive = hiveRepository.save(hive);

        return toDTO(savedHive);
    }

    // Atualizar
    @Transactional
    public HiveDTO update(Long id, HiveDTO dto) {
        Hive hiveToUpdate = hiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Colmeia não encontrada com o ID: " + id));

        hiveToUpdate.setBeeSpecies(dto.beeSpecies());
        hiveToUpdate.setLastHarvest(dto.lastHarvest());
        hiveToUpdate.setInstallationDate(dto.installationDate());
        hiveToUpdate.setStatus(dto.status());
        hiveToUpdate.setNickname(dto.nickname());
        hiveToUpdate.setPopulation(dto.population());
        hiveToUpdate.setProduction(dto.production());
        hiveToUpdate.setCoordinates(dto.coordinates());
        hiveToUpdate.setInspectionNote(dto.inspectionNote());

        if (dto.propertyId() != null) {
            Property property = propertyRepository.findById(dto.propertyId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Propriedade não encontrada com o ID: " + dto.propertyId()));
            hiveToUpdate.setProperty(property);
        } else {
            hiveToUpdate.setProperty(null);
        }

        if (dto.deviceId() != null) {
            Device device = deviceRepository.findById(dto.deviceId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Dispositivo não encontrado com o ID: " + dto.deviceId()));
            hiveToUpdate.setDevice(device);
        } else {
            hiveToUpdate.setDevice(null);
        }

        Hive updatedHive = hiveRepository.save(hiveToUpdate);
        return toDTO(updatedHive);
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
                hive.getDevice() != null ? hive.getDevice().getId() : null);
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
