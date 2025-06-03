package com.project.ibtss.repository;

import com.project.ibtss.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByAccount_IdAndStatusAndType(Integer accountId, String status, String type);
//    Optional<Token> findByValueAndTypeAndUser(String value, String type, User user);
    Optional<Token> findByValue(String value);
}
