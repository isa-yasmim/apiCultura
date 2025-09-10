package br.edu.utfpr.apicultura.app.Model;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.utfpr.apicultura.app.enums.DeviceStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_Device")
public class Device extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 50)
    private String version;

    @Column(name = "battery_status")
    private Integer batteryStatus; 

    @Column(name = "power_source", length = 50)
    private String powerSource; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeviceStatus status;

    @Column(name = "last_measurement_date")
    private LocalDateTime lastMeasurementDate;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Sensor> sensors;
}