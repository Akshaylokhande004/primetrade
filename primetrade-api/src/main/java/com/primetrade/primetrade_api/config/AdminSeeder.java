package com.primetrade.primetrade_api.config;

import com.primetrade.primetrade_api.model.Role;
import com.primetrade.primetrade_api.model.User;
import com.primetrade.primetrade_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.primetrade.primetrade_api.repository.UserRepository;
import com.primetrade.primetrade_api.model.User;
import com.primetrade.primetrade_api.model.Role;

@Component
@Profile("!test")
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL:}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    public AdminSeeder(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (adminEmail == null || adminEmail.isBlank()) {
            return;
        }

        User existingAdmin = userRepository.findByEmail(adminEmail).orElse(null);

        if (existingAdmin == null) {

            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("Admin created.");

        } else {

            existingAdmin.setPassword(passwordEncoder.encode(adminPassword));
            existingAdmin.setRole(Role.ADMIN);
            userRepository.save(existingAdmin);

            System.out.println("Admin updated.");
        }
    }
}