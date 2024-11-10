package org.example.javaspringboot.dto;

import org.example.javaspringboot.model.House;

import java.util.List;

public class UserDTO {

    private int id;
    private String userName;
    private String email;
    private List<House> houses;

    public UserDTO(int id, String userName, String email, List<House> houses) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.houses = houses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<House> getHouses() {
        return this.houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }
}