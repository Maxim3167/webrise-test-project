package com.maxim.webrise.service;

import com.maxim.webrise.repository.UserRepository;
import com.maxim.webrise.repository.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        User userSaved = userRepository.save(user);
        log.debug("User with email " + userSaved.getEmail() + " successfully saved");
        return userSaved;
    }

    public Optional<User> get(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.warn("User with id " + id + " is not found");
        }
        return userOptional;
    }

    public User update(Long id, User updatedUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with id " + id + " is not found");
        }
        User findUser = user.get();
        findUser.setName(updatedUser.getName());
        findUser.setEmail(updatedUser.getEmail());
        return userRepository.save(findUser);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " is not found"));
        userRepository.delete(user);
        log.debug("User with id " + id + " successfully deleted");
    }
}