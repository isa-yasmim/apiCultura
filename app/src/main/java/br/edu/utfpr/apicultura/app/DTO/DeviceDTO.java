package br.edu.utfpr.apicultura.app.DTO;

import br.edu.utfpr.apicultura.app.enums.DeviceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Representa um dispositivo")
public class DeviceDTO {

    @Schema(description = "ID único do dispositivo", example = "1")
    private Long id;

    @Schema(description = "Modelo do dispositivo", example = "ESP32")
    @NotBlank(message = "O modelo não pode ser nulo ou vazio.")
    @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres.")
    private String model;

    @Schema(description = "Versão do dispositivo", example = "v1.0")
    @NotBlank(message = "A versão não pode ser nula ou vazia.")
    @Size(max = 50, message = "A versão deve ter no máximo 50 caracteres.")
    private String version;

    @Schema(description = "Nível da bateria em porcentagem", example = "85")
    @PositiveOrZero(message = "O status da bateria deve ser um valor positivo ou zero.")
    private Integer batteryStatus;

    @Schema(description = "Fonte de energia utilizada pelo dispositivo", example = "Painel Solar")
    @Size(max = 50, message = "A fonte de energia deve ter no máximo 50 caracteres.")
    private String powerSource;

    @Schema(description = "Status atual do dispositivo", example = "ACTIVE")
    @NotNull(message = "O status do dispositivo não pode ser nulo.")
    private DeviceStatus status;
}