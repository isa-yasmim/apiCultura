package br.edu.utfpr.apicultura.app.Controller;

import br.edu.utfpr.apicultura.app.DTO.HiveDTO;
import br.edu.utfpr.apicultura.app.Service.HiveService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hives")
@RequiredArgsConstructor
public class HiveController {

    private final HiveService hiveService;

    // Listagem paginada
    @GetMapping
    public ResponseEntity<Page<HiveDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(hiveService.findAll(pageable));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<HiveDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(hiveService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar
    @PostMapping
    public ResponseEntity<HiveDTO> create(@RequestBody HiveDTO dto) {
        HiveDTO created = hiveService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<HiveDTO> update(@PathVariable Long id, @RequestBody HiveDTO dto) {
        try {
            return ResponseEntity.ok(hiveService.update(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            hiveService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}