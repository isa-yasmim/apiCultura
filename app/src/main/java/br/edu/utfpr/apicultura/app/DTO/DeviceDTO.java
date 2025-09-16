package br.edu.utfpr.apicultura.app.DTO;

import br.edu.utfpr.apicultura.app.enums.DeviceStatus;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeviceDTO {

    private Long id;

    @NotBlank(message = "O modelo não pode ser nulo ou vazio.")
    @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres.")
    private String model;

    @NotBlank(message = "A versão não pode ser nula ou vazia.")
    @Size(max = 50, message = "A versão deve ter no máximo 50 caracteres.")
    private String version;

    @PositiveOrZero(message = "O status da bateria deve ser um valor positivo ou zero.")
    private Integer batteryStatus;

    @Size(max = 50, message = "A fonte de energia deve ter no máximo 50 caracteres.")
    private String powerSource;

    @NotNull(message = "O status do dispositivo não pode ser nulo.")
    private DeviceStatus status;
}