package br.edu.utfpr.apicultura.app.Controller;

import java.net.URI;

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor 
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<Page<DeviceDTO>> findAll(Pageable pageable) {
        Page<DeviceDTO> devices = deviceService.findAll(pageable);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> findById(@PathVariable Long id) {
        DeviceDTO deviceDTO = deviceService.findById(id);
        return ResponseEntity.ok(deviceDTO);
    }

    @PostMapping
    public ResponseEntity<DeviceDTO> create(@Valid @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO newDevice = deviceService.save(deviceDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDevice.getId()).toUri();
        return ResponseEntity.created(location).body(newDevice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> update(@PathVariable Long id, @Valid @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO updatedDevice = deviceService.update(id, deviceDTO);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}