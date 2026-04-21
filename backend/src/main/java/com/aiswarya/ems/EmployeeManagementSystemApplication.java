package com.aiswarya.ems;

import com.aiswarya.ems.entity.User;
import com.aiswarya.ems.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}
	/*@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
		return args -> {

			System.out.println("🔥 CHECKING ADMIN USER...");

			Optional<User> existing = repo.findByUsername("admin");

			if (existing.isEmpty()) {
				System.out.println("🔥 CREATING ADMIN USER...");

				User user = new User();
				user.setUsername("admin");
				user.setPassword(encoder.encode("admin123"));
				user.setRole("ROLE_ADMIN");

				repo.save(user);
			} else {
				System.out.println("✅ ADMIN ALREADY EXISTS");
			}
		};
	}*/
}