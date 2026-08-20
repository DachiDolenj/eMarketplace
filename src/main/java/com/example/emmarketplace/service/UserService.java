package com.example.emmarketplace.service;

import com.example.emmarketplace.entity.User;
import com.example.emmarketplace.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {

    private final UserRepository repo;
    private final EmailValidator emailValidator = EmailValidator.getInstance();
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void register(User u) {
        if (!u.getUsername().matches("^[a-zA-Z0-9]{8,20}$")) {
            throw new RuntimeException("Invalid username: must be 8–20 letters/digits");
        }

        if (!emailValidator.isValid(u.getEmail())) {
            throw new RuntimeException("Invalid email address");
        }

        int age = Period.between(u.getBirthday(), LocalDate.now()).getYears();
        if (age < 14) {
            throw new RuntimeException("User must be older than 13");
        }

        u.setPassword(encoder.encode(u.getPassword()));

        repo.save(u);
    }

    public User login(String login, String rawPassword) {
        User u = repo.findByEmailOrUsername(login);
        if (u != null && encoder.matches(rawPassword, u.getPassword())) {
            return u;
        }
        throw new RuntimeException("Invalid credentials");
    }
}
