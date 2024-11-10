package org.example.javaspringboot.service;

import org.example.javaspringboot.exception.ResourceNotFoundException;
import org.example.javaspringboot.model.Device;
import org.example.javaspringboot.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> getActiveDevices() {
        return deviceRepository.findByIsActiveTrue();
    }

    public List<Device> getDevicesByType(String type) {
        return deviceRepository.findByType(type);
    }

    public List<Device> getDevicesByHouseId(int houseId) {
        return deviceRepository.findByHouseId(houseId);
    }

    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device getDeviceById(int id) {
        return deviceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device with ID " + id + " not found"));
    }

    public void deleteDevice(int id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device with ID " + id + " not found");
        }
        deviceRepository.deleteById(id);
    }
}
