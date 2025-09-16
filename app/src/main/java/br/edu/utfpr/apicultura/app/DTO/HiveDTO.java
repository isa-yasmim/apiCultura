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
