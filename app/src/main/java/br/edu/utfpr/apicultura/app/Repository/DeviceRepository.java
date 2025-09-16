package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.apicultura.app.Model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {}