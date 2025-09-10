package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apicultura.app.Model.Hive;

public interface HiveRepository extends JpaRepository<Hive, Long> {

}
