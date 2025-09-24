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

        existingProperty.setAddress(propertyDTO.getAddress());
        existingProperty.setNumber(propertyDTO.getNumber());
        existingProperty.setName(propertyDTO.getName());
        existingProperty.setDescription(propertyDTO.getDescription());

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
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setNumber(property.getNumber());
        propertyDTO.setName(property.getName());
        propertyDTO.setDescription(property.getDescription());
        // Note: hives list is not being set here as it might require additional logic
        return propertyDTO;
    }

    private Property convertToEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setAddress(propertyDTO.getAddress());
        property.setNumber(propertyDTO.getNumber());
        property.setName(propertyDTO.getName());
        property.setDescription(propertyDTO.getDescription());
        return property;
    }
}