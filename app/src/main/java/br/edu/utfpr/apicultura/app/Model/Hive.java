package br.edu.utfpr.apicultura.app.Model;

import java.time.LocalDate;

import br.edu.utfpr.apicultura.app.enums.HiveStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_Hive")
public class Hive extends BaseEntity {

    @Column(name = "bee_species", length = 100)
    private String beeSpecies;

    @Column(name = "last_harvest")
    private LocalDate lastHarvest;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HiveStatus status;

    @Column(length = 100)
    private String nickname;

    private Integer population;

    private Double production;

    @Column(length = 50)
    private String coordinates;

    @Column(name = "inspection_note")
    private Integer inspectionNote;

    // --- Relacionamento ManyToOne com Property ---
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "property_id", referencedColumnName = "id", nullable = false)
    private Property property;

     // --- Relacionamento OneToOne com Device ---
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;
}