package saphy.saphy.item.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import saphy.saphy.item.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
