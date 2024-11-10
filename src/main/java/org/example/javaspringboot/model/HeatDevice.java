package org.example.javaspringboot.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("HEAT_DEVICE")
@Builder
@AllArgsConstructor
public class HeatDevice extends Device {

    private int temperature;

    public HeatDevice() {
        this.setType(DeviceType.HEAT_DEVICE);
        this.temperature = 20;
    }

    @Override
    public String toString() {
        return "HeatDevice {" +
                "id=" + getId() +
                ", name=" + getName() +
                ", model=" + getModel() +
                ", type=" + getType() +
                ", house=" + (getHouseAddress() != null ? getHouseAddress() : "no house assigned") +
                ", temperature=" + temperature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeatDevice that = (HeatDevice) o;
        return this.getId() == that.getId();
    }
}
