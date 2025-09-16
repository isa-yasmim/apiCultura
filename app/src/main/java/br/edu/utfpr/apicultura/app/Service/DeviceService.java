package br.edu.utfpr.apicultura.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.apicultura.app.DTO.DeviceDTO;
import br.edu.utfpr.apicultura.app.Model.Device;
import br.edu.utfpr.apicultura.app.Repository.DeviceRepository;
import br.edu.utfpr.apicultura.app.Exception.ResourceNotFoundException;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // Método para listagem paginada de todos os devices
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findAll(Pageable pageable) {
        Page<Device> devices = deviceRepository.findAll(pageable);
        return devices.map(this::toDTO);
    }

    // Método para buscar um device por ID
    @Transactional(readOnly = true)
    public DeviceDTO findById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com o ID: " + id));
        return toDTO(device);
    }

    // Método para criar um novo device
    @Transactional
    public DeviceDTO save(DeviceDTO deviceDTO) {
        Device device = toEntity(deviceDTO);
        device = deviceRepository.save(device);
        return toDTO(device);
    }

    // Método para atualizar um device existente
    @Transactional
    public DeviceDTO update(Long id, DeviceDTO deviceDTO) {
        // Busca o dispositivo no banco. Se não existir, lança a exceção.
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Não é possível atualizar. Dispositivo não encontrado com o ID: " + id));

        // Atualiza os campos do objeto encontrado com os dados do DTO
        existingDevice.setModel(deviceDTO.getModel());
        existingDevice.setVersion(deviceDTO.getVersion());
        existingDevice.setBatteryStatus(deviceDTO.getBatteryStatus());
        existingDevice.setPowerSource(deviceDTO.getPowerSource());
        existingDevice.setStatus(deviceDTO.getStatus());

        Device updatedDevice = deviceRepository.save(existingDevice);
        return toDTO(updatedDevice);
    }

    // Método para deletar um device
    @Transactional
    public void delete(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não é possível deletar. Dispositivo não encontrado com o ID: " + id);
        }
        deviceRepository.deleteById(id);
    }

    // Métodos auxiliares de Mapeamento DTO <-> Entity

    private DeviceDTO toDTO(Device entity) {
        DeviceDTO dto = new DeviceDTO();
        dto.setId(entity.getId());
        dto.setModel(entity.getModel());
        dto.setVersion(entity.getVersion());
        dto.setBatteryStatus(entity.getBatteryStatus());
        dto.setPowerSource(entity.getPowerSource());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private Device toEntity(DeviceDTO dto) {
        Device entity = new Device();
        entity.setModel(dto.getModel());
        entity.setVersion(dto.getVersion());
        entity.setBatteryStatus(dto.getBatteryStatus());
        entity.setPowerSource(dto.getPowerSource());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}