package com.project.ibtss.repository;

import com.project.ibtss.model.Token;
import com.project.ibtss.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByUser_IdAndStatusAndType(UUID userId, String status, String type);
    Optional<Token> findByValueAndTypeAndUser(String value, String type, User user);
    Optional<Token> findByValue(String value);
}
