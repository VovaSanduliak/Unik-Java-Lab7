package org.example.javaspringboot.repository;

import org.example.javaspringboot.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findByHouseId(int houseId);
    List<Device> findByType(String type);
    List<Device> findByIsActiveTrue();
}
