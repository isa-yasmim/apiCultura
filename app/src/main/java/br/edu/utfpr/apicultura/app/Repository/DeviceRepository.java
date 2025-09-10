package br.edu.utfpr.apicultura.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apicultura.app.Model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}