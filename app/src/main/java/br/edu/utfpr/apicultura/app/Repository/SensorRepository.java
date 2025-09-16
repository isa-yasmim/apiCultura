package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.apicultura.app.Model.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {}
