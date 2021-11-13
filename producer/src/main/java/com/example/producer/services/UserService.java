package com.example.producer.services;

import com.example.producer.entities.User;
import com.example.producer.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return null;
        }
        return userOptional.get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
