package br.edu.utfpr.apicultura.app.DTO;

import br.edu.utfpr.apicultura.app.enums.SensorStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SensorDTO {

    private Long id;

    @NotBlank(message = "O tipo do sensor não pode ser nulo ou vazio.")
    @Size(max = 50, message = "O tipo do sensor deve ter no máximo 50 caracteres.")
    private String type;

    @Size(max = 20, message = "A unidade de medida deve ter no máximo 20 caracteres.")
    private String measurementUnit;

    private Double lastValue;

    @NotNull(message = "O status do sensor não pode ser nulo.")
    private SensorStatus status;

    private Double minLimit;

    private Double maxLimit;

    private LocalDate installationDate;

    @NotNull(message = "O deviceId é obrigatório.")
    private Long deviceId;
}
