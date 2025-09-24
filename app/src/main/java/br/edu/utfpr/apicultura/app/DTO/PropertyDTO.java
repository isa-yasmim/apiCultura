package br.edu.utfpr.apicultura.app.DTO;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PropertyDTO {

    @NotBlank(message = "O endereço não pode ser nulo ou vazio.")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres.")
    private String address;

    @NotBlank(message = "O número não pode ser nulo ou vazio.")
    @Size(max = 20, message = "O número deve ter no máximo 20 caracteres.")
    private String number;

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String name;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    private String description;

    @Valid
    private List<HiveDTO> hives;
}
