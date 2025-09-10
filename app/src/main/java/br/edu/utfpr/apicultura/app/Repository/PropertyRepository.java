package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apicultura.app.Model.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
