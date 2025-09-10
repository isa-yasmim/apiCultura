package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apicultura.app.Model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

}
