package com.project.ibtss.repository;

import com.project.ibtss.model.Account;
import com.project.ibtss.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByAccount(Account account);
}
