package org.example.javaspringboot.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.javaspringboot.model.House;

@Getter
@Setter
public class HouseDTO {

    private int id;
    private String name;
    private String address;

    public HouseDTO(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static HouseDTO fromEntity(House house) {
        return new HouseDTO(house.getId(), house.getName(), house.getAddress());
    }
}
