package br.edu.utfpr.apicultura.app.Controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.utfpr.apicultura.app.DTO.DeviceDTO;
import br.edu.utfpr.apicultura.app.Service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor 
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "Listar dispositivos", description = "Retorna todos os dispositivos com paginação")
    @ApiResponse(responseCode = "200", description = "Lista de dispositivos retornada com sucesso",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DeviceDTO.class)))
    @GetMapping
    public ResponseEntity<Page<DeviceDTO>> findAll(Pageable pageable) {
        Page<DeviceDTO> devices = deviceService.findAll(pageable);
        return ResponseEntity.ok(devices);
    }

    @Operation(summary = "Buscar dispositivo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dispositivo encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DeviceDTO.class))),
        @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> findById(
            @Parameter(description = "ID do dispositivo", example = "1") @PathVariable Long id) {
        DeviceDTO deviceDTO = deviceService.findById(id);
        return ResponseEntity.ok(deviceDTO);
    }

    @Operation(summary = "Criar novo dispositivo")
    @ApiResponse(responseCode = "201", description = "Dispositivo criado com sucesso",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DeviceDTO.class)))
    @PostMapping
    public ResponseEntity<DeviceDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do novo dispositivo", required = true,
                content = @Content(schema = @Schema(implementation = DeviceDTO.class)))
            @Valid @RequestBody DeviceDTO deviceDTO) {

        DeviceDTO newDevice = deviceService.save(deviceDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDevice.getId()).toUri();
        return ResponseEntity.created(location).body(newDevice);
    }

    @Operation(summary = "Atualizar dispositivo existente")
    @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DeviceDTO.class)))
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> update(
            @Parameter(description = "ID do dispositivo a ser atualizado", example = "1") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados atualizados do dispositivo", required = true,
                content = @Content(schema = @Schema(implementation = DeviceDTO.class)))
            @Valid @RequestBody DeviceDTO deviceDTO) {

        DeviceDTO updatedDevice = deviceService.update(id, deviceDTO);
        return ResponseEntity.ok(updatedDevice);
    }

    @Operation(summary = "Excluir dispositivo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Dispositivo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do dispositivo a ser excluído", example = "1") @PathVariable Long id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}