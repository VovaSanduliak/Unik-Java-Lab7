package org.example.javaspringboot.controller;

import org.example.javaspringboot.dto.HouseDTO;
import org.example.javaspringboot.exception.BadRequestException;
import org.example.javaspringboot.exception.ResourceNotFoundException;
import org.example.javaspringboot.model.House;
import org.example.javaspringboot.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public ResponseEntity<List<HouseDTO>> getAllHouses() {
        List<House> houses = houseService.getAllHouses();

        List<HouseDTO> houseDTOs = houses.stream()
                .map(HouseDTO::fromEntity)
                .collect(Collectors.toList());

        return new ResponseEntity<>(houseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable int id) {
        House house = houseService.getHouseById(id);

        if (house == null) {
            throw new ResourceNotFoundException("House with ID " + id + " not found");
        }

        return new ResponseEntity<>(HouseDTO.fromEntity(house), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HouseDTO> createHouse(@RequestBody House house) {
        try {
            House savedHouse = houseService.saveHouse(house);
            return new ResponseEntity<>(HouseDTO.fromEntity(savedHouse), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create house: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseDTO> updateHouse(@PathVariable int id, @RequestBody House house) {
        House existingHouse = houseService.getHouseById(id);
        if (existingHouse == null) {
            throw new ResourceNotFoundException("House with ID " + id + " not found");
        }
        house.setId(id);
        House updatedHouse = houseService.saveHouse(house);
        return new ResponseEntity<>(HouseDTO.fromEntity(updatedHouse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable int id) {
        House existingHouse = houseService.getHouseById(id);
        if (existingHouse == null) {
            throw new ResourceNotFoundException("House with ID " + id + " not found");
        }
        houseService.deleteHouse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<HouseDTO> getHouseByAddress(@PathVariable String address) {
        House house = houseService.getHouseByAddress(address);
        if (house == null) {
            throw new ResourceNotFoundException("House with address " + address + " not found");
        }
        return new ResponseEntity<>(HouseDTO.fromEntity(house), HttpStatus.OK);
    }
}
