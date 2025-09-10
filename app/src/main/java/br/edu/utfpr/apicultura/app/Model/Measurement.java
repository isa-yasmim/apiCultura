package br.edu.utfpr.apicultura.app.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "TB_Measurement")
public class Measurement extends BaseEntity {

    /*@Id
    private UUID id = UUID.randomUUID();

    @OneToMany
    private Sensor sensor;

    @Column(nullable=false)
    private String medida;

    private LocalDateTime dataHora;

    public void setSensor(Sensor sensor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
}