package com.project.ibtss.repository;

import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import com.project.ibtss.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String username);
    Account findByFullName(String fullName);
    Account findByPhone(String phone);
    List<Account> findByRoleAndStaff_Position(Role role, Position position);
}
