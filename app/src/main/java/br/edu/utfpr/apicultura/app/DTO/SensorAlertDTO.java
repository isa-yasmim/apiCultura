package br.edu.utfpr.apicultura.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorAlertDTO {
    private Long sensorId;
    private String sensorType;
    private Double valorRegistrado;
    private Double limite;
    private String tipoAlerta; // "ABAIXO_MINIMO" ou "ACIMA_MAXIMO"
    private LocalDateTime timestamp;
}