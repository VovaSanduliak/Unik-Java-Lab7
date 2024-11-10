package org.example.javaspringboot.service;

import org.example.javaspringboot.exception.ResourceNotFoundException;
import org.example.javaspringboot.model.House;
import org.example.javaspringboot.repository.HouseRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {
        try {
            return houseRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Could not retrieve houses", e);
        }
    }

    public House getHouseById(int id) {
        return houseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House with id " + id + " not found"));
    }

    public House saveHouse(House house) {
        try {
            return houseRepository.save(house);
        } catch (Exception e) {
            throw new ServiceException("Could not save house", e);
        }
    }

    public void deleteHouse(int id) {
        if (!houseRepository.existsById(id)) {
            throw new ResourceNotFoundException("House with id " + id + " not found");
        }
        try {
            houseRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Could not delete house", e);
        }
    }

    public House getHouseByAddress(String address) {
        return houseRepository
                .findByAddress(address)
                .orElseThrow(() -> new ResourceNotFoundException("House with address " + address + " not found"));
    }
}
