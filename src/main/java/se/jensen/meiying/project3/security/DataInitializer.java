package se.jensen.meiying.project3.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.meiying.project3.model.User;
import se.jensen.meiying.project3.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@example.com");
                admin.setDisplayName("Administrator");
                admin.setBio("System Administrator");
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Admin user created: admin / admin123");
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEmail("user@example.com");
                user.setDisplayName("Regular User");
                user.setBio("Regular user account");
                user.setRole("USER");
                userRepository.save(user);
                System.out.println("User created: user / user123");
            }
        };
    }
}