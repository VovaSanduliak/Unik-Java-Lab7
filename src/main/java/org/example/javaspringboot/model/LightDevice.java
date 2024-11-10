package org.example.javaspringboot.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@DiscriminatorValue("LIGHT_DEVICE")
@Builder
@AllArgsConstructor
public class LightDevice extends Device {

    @Min(value = 10,  message = "Brightness cannot be lower  than 0")
    @Max(value = 100, message = "Brightness cannot be higher than 0")
    private int brightness;

    public LightDevice() {
        this.setType(DeviceType.LIGHT_DEVICE);
        this.brightness = 50;
    }

    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100)
            throw new IllegalArgumentException("Brightness must be from 0 to 100");
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "LightDevice {" +
                "id=" + getId() +
                ", name=" + getName() +
                ", model=" + getModel() +
                ", type=" + getType() +
                ", house=" + (getHouseAddress() != null ? getHouseAddress() : "no house assigned") +
                ", brightness=" + brightness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightDevice that = (LightDevice) o;
        return this.getId() == that.getId();
    }
}
