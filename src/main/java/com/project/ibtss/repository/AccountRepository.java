package com.project.ibtss.repository;

import com.project.ibtss.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByFullName(String fullName);
    Account findByPhoneNumber(String phoneNumber);
    Account findByEmail(String email);
}
