package org.example.javaspringboot.repository;

import org.example.javaspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
}
