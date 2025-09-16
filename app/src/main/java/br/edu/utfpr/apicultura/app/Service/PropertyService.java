package br.edu.utfpr.apicultura.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.apicultura.app.DTO.PropertyDTO;
import br.edu.utfpr.apicultura.app.Model.Property;
import br.edu.utfpr.apicultura.app.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Transactional(readOnly = true)
    public List<PropertyDTO> findAll() {
        return propertyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PropertyDTO findById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
        return convertToDTO(property);
    }

    @Transactional
    public PropertyDTO create(PropertyDTO propertyDTO) {
        Property property = convertToEntity(propertyDTO);
        Property savedProperty = propertyRepository.save(property);
        return convertToDTO(savedProperty);
    }

    @Transactional
    public PropertyDTO update(Long id, PropertyDTO propertyDTO) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));

        existingProperty.setAddress(propertyDTO.address());
        existingProperty.setNumber(propertyDTO.number());
        existingProperty.setName(propertyDTO.name());
        existingProperty.setDescription(propertyDTO.description());

        Property updatedProperty = propertyRepository.save(existingProperty);
        return convertToDTO(updatedProperty);
    }

    @Transactional
    public void delete(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
        propertyRepository.delete(property);
    }

    private PropertyDTO convertToDTO(Property property) {
        return new PropertyDTO(
                property.getAddress(),
                property.getNumber(),
                property.getName(),
                property.getDescription(),
                null // hives
        );
    }

    private Property convertToEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setAddress(propertyDTO.address());
        property.setNumber(propertyDTO.number());
        property.setName(propertyDTO.name());
        property.setDescription(propertyDTO.description());
        return property;
    }
}