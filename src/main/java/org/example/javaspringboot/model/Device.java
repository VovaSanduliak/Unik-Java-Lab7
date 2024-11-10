package org.example.javaspringboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Abstract class representing a generic device.
 * This class is mapped to the database using JPA.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "device_type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HeatDevice.class, name = "HEAT_DEVICE"),
        @JsonSubTypes.Type(value = LightDevice.class, name = "LIGHT_DEVICE")
})
@Getter
@Setter
public abstract class Device implements Comparable<Device> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Device name cannot be empty")
    @Size(max = 50, message = "Device name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Model cannot be empty")
    @Size(max = 50, message = "Model name must be less than 50 characters")
    private String model;

    @NotBlank(message = "Serial nubmer cannot be empty")
    @Size(max = 100, message = "Serial number must be less than 100 characters")
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @NotNull(message = "Device type is required")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private LocalDateTime lastTurnedOnAt;

    @Min(value = 0, message = "Warranty duration must be positive value")
    private int warrantyDurationInMonths;
    private boolean isActive;

    public Device() {
        this.createdAt = LocalDateTime.now();
        this.lastTurnedOnAt = null;
    }

    public String getHouseAddress() {
        return (getHouse() != null) ? getHouse().getAddress() : "no house assigned";
    }

    public Duration getWorkingTime() {
        if (lastTurnedOnAt == null) {
            return Duration.ZERO;
        }
        return Duration.between(lastTurnedOnAt, LocalDateTime.now());
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean turnOn() {
        this.isActive = true;
        this.lastTurnedOnAt = LocalDateTime.now();
        return true;
    }

    public boolean turnOff() {
        this.isActive = false;
        this.lastTurnedOnAt = null;
        return true;
    }

    @Override
    public int compareTo(Device d) {
        return this.name.compareTo(d.name);
    }

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, model);
    }
}