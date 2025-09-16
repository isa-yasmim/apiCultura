package br.edu.utfpr.apicultura.app.Service;

import br.edu.utfpr.apicultura.app.DTO.SensorDTO;
import br.edu.utfpr.apicultura.app.Model.Device;
import br.edu.utfpr.apicultura.app.Model.Sensor;
import br.edu.utfpr.apicultura.app.Repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    // Listagem paginada
    public Page<SensorDTO> findAll(Pageable pageable) {
        return sensorRepository.findAll(pageable).map(this::toDTO);
    }

    // Buscar por ID
    public SensorDTO findById(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor não encontrado com id " + id));
        return toDTO(sensor);
    }

    // Criar
    public SensorDTO create(SensorDTO dto) {
        Sensor sensor = toEntity(dto);
        return toDTO(sensorRepository.save(sensor));
    }

    // Atualizar
    public SensorDTO update(Long id, SensorDTO dto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor não encontrado com id " + id));

        sensor.setType(dto.getType());
        sensor.setMeasurementUnit(dto.getMeasurementUnit());
        sensor.setLastValue(dto.getLastValue());
        sensor.setStatus(dto.getStatus());
        sensor.setMinLimit(dto.getMinLimit());
        sensor.setMaxLimit(dto.getMaxLimit());
        sensor.setInstallationDate(dto.getInstallationDate());

        if (dto.getDeviceId() != null) {
            Device device = new Device();
            device.setId(dto.getDeviceId());
            sensor.setDevice(device);
        }

        return toDTO(sensorRepository.save(sensor));
    }

    // Deletar
    public void delete(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new EntityNotFoundException("Sensor não encontrado com id " + id);
        }
        sensorRepository.deleteById(id);
    }

    // ====== Mapeamento DTO <-> Entity ======

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setType(sensor.getType());
        dto.setMeasurementUnit(sensor.getMeasurementUnit());
        dto.setLastValue(sensor.getLastValue());
        dto.setStatus(sensor.getStatus());
        dto.setMinLimit(sensor.getMinLimit());
        dto.setMaxLimit(sensor.getMaxLimit());
        dto.setInstallationDate(sensor.getInstallationDate());
        dto.setDeviceId(sensor.getDevice() != null ? sensor.getDevice().getId() : null);
        return dto;
    }

    private Sensor toEntity(SensorDTO dto) {
        Sensor sensor = new Sensor();
        sensor.setId(dto.getId());
        sensor.setType(dto.getType());
        sensor.setMeasurementUnit(dto.getMeasurementUnit());
        sensor.setLastValue(dto.getLastValue());
        sensor.setStatus(dto.getStatus());
        sensor.setMinLimit(dto.getMinLimit());
        sensor.setMaxLimit(dto.getMaxLimit());
        sensor.setInstallationDate(dto.getInstallationDate());

        if (dto.getDeviceId() != null) {
            Device device = new Device();
            device.setId(dto.getDeviceId());
            sensor.setDevice(device);
        }

        return sensor;
    }
}
