package br.edu.utfpr.apicultura.app.Service;

import br.edu.utfpr.apicultura.app.DTO.SensorAlertDTO;
import br.edu.utfpr.apicultura.app.DTO.SensorDTO;
import br.edu.utfpr.apicultura.app.Model.Device;
import br.edu.utfpr.apicultura.app.Model.Sensor;
import br.edu.utfpr.apicultura.app.Repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.MessagePostProcessor; // <-- NOVO IMPORT OBRIGATÓRIO AQUI
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    // --- ADIÇÕES RABBITMQ ---

    // Injeta o template do RabbitMQ
    private final RabbitTemplate rabbitTemplate;

    // Injeta os nomes definidos no application.properties
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // --- FIM ADIÇÕES RABBITMQ ---

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
        Sensor savedSensor = sensorRepository.save(sensor); // Salva primeiro

        // ** ADIÇÃO: Verifica os limites após criar **
        checkAndSendAlerts(savedSensor);

        return toDTO(savedSensor); // Retorna o DTO do sensor salvo
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

        Sensor updatedSensor = sensorRepository.save(sensor); // Salva as atualizações

        // ** ADIÇÃO: Verifica os limites após atualizar **
        checkAndSendAlerts(updatedSensor);

        return toDTO(updatedSensor); // Retorna o DTO do sensor atualizado
    }

    // Deletar
    public void delete(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new EntityNotFoundException("Sensor não encontrado com id " + id);
        }
        sensorRepository.deleteById(id);
    }

    // --- NOVO MÉTODO PRIVADO PARA CHECAR ALERTAS CORRIGIDO ---

    /**
     * Verifica o último valor do sensor contra seus limites e, se necessário,
     * envia uma mensagem de alerta para o RabbitMQ.
     * @param sensor O sensor (já salvo) para verificar.
     */
    private void checkAndSendAlerts(Sensor sensor) {
        // Garante que temos valores para comparar
        if (sensor.getLastValue() == null) {
            return; // Sem valor, sem alerta.
        }

        String alertType = null;
        Double limit = null;

        // 1. Verifica se ultrapassou o MÁXIMO (se o máximo existe)
        if (sensor.getMaxLimit() != null && sensor.getLastValue() > sensor.getMaxLimit()) {
            alertType = "ACIMA_MAXIMO";
            limit = sensor.getMaxLimit();
        }
        // 2. Verifica se caiu abaixo do MÍNIMO (se o mínimo existe)
        else if (sensor.getMinLimit() != null && sensor.getLastValue() < sensor.getMinLimit()) {
            alertType = "ABAIXO_MINIMO";
            limit = sensor.getMinLimit();
        }

        // 3. Se um tipo de alerta foi definido, envia a mensagem
        if (alertType != null) {
            SensorAlertDTO alertDTO = new SensorAlertDTO(
                    sensor.getId(),
                    sensor.getType(),
                    sensor.getLastValue(),
                    limit,
                    alertType,
                    LocalDateTime.now()
            );

            // FIX: Cria um processador para adicionar os cabeçalhos de Content-Type
            // Isso resolve o erro "Unexpected token" no consumidor Node.js.
            MessagePostProcessor postProcessor = message -> {
                message.getMessageProperties().setContentType("application/json");
                message.getMessageProperties().setContentEncoding("UTF-8");
                return message;
            };

            // Linha de envio CORRIGIDA: usa o postProcessor
            rabbitTemplate.convertAndSend(exchangeName, routingKey, alertDTO, postProcessor);

            // Log para debug no console
            System.out.println("[SensorService] ALERTA ENVIADO (Content-Type fixo): " + alertDTO.toString());
        }
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