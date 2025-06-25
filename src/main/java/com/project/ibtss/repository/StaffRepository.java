package com.project.ibtss.repository;

import com.project.ibtss.model.Customer;
import com.project.ibtss.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Optional<Staff> findByAccountId(int accountId);

}
