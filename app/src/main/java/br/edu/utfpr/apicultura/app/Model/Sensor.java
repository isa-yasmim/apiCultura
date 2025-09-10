package br.edu.utfpr.apicultura.app.Model;

import java.time.LocalDate;

import br.edu.utfpr.apicultura.app.enums.SensorStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_Sensor")
public class Sensor extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String type; 

    @Column(name = "measurement_unit", length = 20)
    private String measurementUnit; 

    @Column(name = "last_value")
    private Double lastValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SensorStatus status;

    @Column(name = "min_limit")
    private Double minLimit;

    @Column(name = "max_limit")
    private Double maxLimit;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}