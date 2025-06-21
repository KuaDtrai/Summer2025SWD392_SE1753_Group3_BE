package com.project.ibtss.repository;

import com.project.ibtss.model.TicketPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPayRepository extends JpaRepository<TicketPay, Integer> {
}
