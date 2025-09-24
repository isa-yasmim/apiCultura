package br.edu.utfpr.apicultura.app.DTO;

import java.time.LocalDate;

import br.edu.utfpr.apicultura.app.enums.HiveStatus;

public record HiveDTO(
        Long id,
        String beeSpecies,
        LocalDate lastHarvest,
        LocalDate installationDate,
        HiveStatus status,
        String nickname,
        Integer population,
        Double production,
        String coordinates,
        Integer inspectionNote,
        Long propertyId,
        Long deviceId
) {}

/*import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HiveDTO {

    private Long id;

    @NotBlank(message = "A espécie de abelha não pode ser nula ou vazia.")
    @Size(max = 100, message = "A espécie de abelha deve ter no máximo 100 caracteres.")
    private String beeSpecies;

    @PastOrPresent(message = "A data da última colheita deve ser no passado ou presente.")
    private LocalDate lastHarvest;

    @PastOrPresent(message = "A data de instalação deve ser no passado ou presente.")
    private LocalDate installationDate;

    @NotNull(message = "O status da colmeia não pode ser nulo.")
    private HiveStatus status;

    @Size(max = 50, message = "O apelido deve ter no máximo 50 caracteres.")
    private String nickname;

    @PositiveOrZero(message = "A população deve ser um valor positivo ou zero.")
    private Integer population;

    @PositiveOrZero(message = "A produção deve ser um valor positivo ou zero.")
    private Double production;

    @Size(max = 100, message = "As coordenadas devem ter no máximo 100 caracteres.")
    private String coordinates;

    @Min(value = 0, message = "A nota de inspeção deve ser no mínimo 0.")
    @Max(value = 10, message = "A nota de inspeção deve ser no máximo 10.")
    private Integer inspectionNote;

    @NotNull(message = "O ID da propriedade não pode ser nulo.")
    private Long propertyId;

    private Long deviceId;
}*/

