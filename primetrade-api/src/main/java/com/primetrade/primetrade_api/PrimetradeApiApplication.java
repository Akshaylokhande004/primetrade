package com.primetrade.primetrade_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class PrimetradeApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(PrimetradeApiApplication.class, args);
	}
    //to create admin
	@Bean
	CommandLineRunner seedAdmin(UserRepository userRepository,
								PasswordEncoder passwordEncoder,
								@Value("${ADMIN_EMAIL:}") String adminEmail,
								@Value("${ADMIN_PASSWORD:}") String adminPassword) {

		return args -> {

			if (adminEmail == null || adminEmail.isBlank()) {
				return; // No admin configured
			}

			if (userRepository.findByEmail(adminEmail).isEmpty()) {

				User admin = new User();
				admin.setEmail(adminEmail);
				admin.setPassword(passwordEncoder.encode(adminPassword));
				admin.setRole(Role.ADMIN);

				userRepository.save(admin);

				System.out.println("Admin user seeded successfully.");
			}
		};
	}

}
