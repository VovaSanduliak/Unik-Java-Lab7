package org.example.javaspringboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a house that can own multiple devices.
 * This class is mapped to the database using JPA.
 */
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    private String name;
    private String address;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Device> devices;

    @ManyToMany(mappedBy = "houses")
    private final List<User> users = new ArrayList<>();

    public House() {
        this.devices = new ArrayList<>();
    }

    public House(String name, String address) {
        this.name = name;
        this.address = address;
        this.devices = new ArrayList<>();
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = address;
    }

    public List<Device> getDevices() {
        return this.devices;
    }

    public void addDevice(Device device) {
        if (device != null) {
            this.devices.add(device);
            device.setHouse(this);
        }
    }

    public void addDevices(List<Device> devices) {
        if (devices != null) {
            devices.forEach(this::addDevice);
        }
    }

    public boolean removeDevice(Device device) {
        if (device != null && this.devices.remove(device)) {
            device.setHouse(null); // Ensure bidirectional mapping
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return id == house.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }
}
