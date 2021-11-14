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

    public User addUser(User user) {
        if (user != null) {
            User addedUser = userRepository.save(user);
            userRepository.flush();
            addedUser.getId();
            return addedUser;
        }
        return null;
    }

    public User updateUser(User user) {
        if (user == null || findById(user.getId()) == null) {
            return null;
        }
        return userRepository.save(user);

    }

    public void deleteById(Long id) {
        if (findById(id) == null) {
            throw new NullPointerException("User not found");
        }
        userRepository.deleteById(id);
    }
}
