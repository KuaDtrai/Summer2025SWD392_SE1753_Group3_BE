package com.project.ibtss.configuration;

import com.project.ibtss.enums.Role;
import com.project.ibtss.model.Account;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (accountRepository.findByFullName("admin") == null) {
                Account account = Account.builder()
                        .fullName("admin")
                        .passwordHash(passwordEncoder.encode("12345"))
                        .fullName("Admin")
                        .role(Role.ADMIN)
                        .isActive(true)
                        .build();

                accountRepository.save(account);
                log.warn("Admin user created, password is: 12345. Please change it if necessary!");
            }
        };
    }
}
