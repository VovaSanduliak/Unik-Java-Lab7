package org.example.javaspringboot.controller;

import jakarta.validation.Valid;
import org.example.javaspringboot.exception.BadRequestException;
import org.example.javaspringboot.exception.ResourceNotFoundException;
import org.example.javaspringboot.model.Device;
import org.example.javaspringboot.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Device>> getActiveDevices() {
        List<Device> devices = deviceService.getActiveDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Device>> getDevicesByType(@PathVariable String type) {
        List<Device> devices = deviceService.getDevicesByType(type);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<Device>> getDevicesByHouseId(@PathVariable int houseId) {
        List<Device> devices = deviceService.getDevicesByHouseId(houseId);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable int id) {
        Device device = deviceService.getDeviceById(id);
        if (device == null) {
            throw new ResourceNotFoundException("Device with ID " + id + " not found");
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        try {
            Device savedDevice = deviceService.saveDevice(device);
            return new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestException("Failed to create device: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable int id, @Valid @RequestBody Device device) {
        Device existingDevice = deviceService.getDeviceById(id);
        if (existingDevice == null) {
            throw new ResourceNotFoundException("Device with ID " + id + " not found");
        }

        device.setId(id);
        Device updatedDevice = deviceService.saveDevice(device);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable int id) {
        Device existingDevice = deviceService.getDeviceById(id);
        if (existingDevice == null) {
            throw new ResourceNotFoundException("Device with ID " + id + " not found");
        }
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
