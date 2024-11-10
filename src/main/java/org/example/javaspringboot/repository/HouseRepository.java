package org.example.javaspringboot.repository;

import org.example.javaspringboot.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Integer> {
    Optional<House> findByAddress(String address);
}
