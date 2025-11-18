package br.edu.utfpr.apicultura.app.Controller;

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

import br.edu.utfpr.apicultura.app.DTO.SensorDTO;
import br.edu.utfpr.apicultura.app.Service.SensorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy; // Import necessário

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    // FIX CRÍTICO: Aplicar @Lazy para quebrar o ciclo de dependência na inicialização do contexto Spring.
    //@Lazy
    private final SensorService sensorService;

    // Listagem paginada
    @GetMapping
    public ResponseEntity<Page<SensorDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(sensorService.findAll(pageable));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(sensorService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar
    @PostMapping
    public ResponseEntity<SensorDTO> create(@Valid @RequestBody SensorDTO dto) {
        SensorDTO created = sensorService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> update(@PathVariable Long id, @Valid @RequestBody SensorDTO dto) {
        try {
            return ResponseEntity.ok(sensorService.update(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            sensorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}