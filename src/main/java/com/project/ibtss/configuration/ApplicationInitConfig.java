package com.project.ibtss.configuration;

import com.project.ibtss.enums.Role;
import com.project.ibtss.model.User;
import com.project.ibtss.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("User with email " + email + "not found"));
    }


    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("12345"))
                        .fullName("Admin")
                        .role(Role.ADMIN)
                        .isActive(true)
                        .build();

                userRepository.save(user);
                log.warn("Admin user created, password is: 12345. Please change it if necessary!");
            }
        };
    }
}
