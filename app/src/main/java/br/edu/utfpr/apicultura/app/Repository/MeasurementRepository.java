package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apicultura.app.Model.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

}
