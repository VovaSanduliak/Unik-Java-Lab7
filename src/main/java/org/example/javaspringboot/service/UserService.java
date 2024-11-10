package org.example.javaspringboot.service;

import org.example.javaspringboot.exception.ResourceNotFoundException;
import org.example.javaspringboot.model.User;
import org.example.javaspringboot.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Could not retrieve users", e);
        }
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ServiceException("Could not save user", e);
        }
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Could not delete user", e);
        }
    }

    public List<User> getUsersByUserName(String userName) {
        try {
            return userRepository.findByUserName(userName);
        } catch (Exception e) {
            throw new ServiceException("Could not get users by userName", e);
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }
}
