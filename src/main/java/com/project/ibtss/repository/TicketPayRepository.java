package com.project.ibtss.repository;

import com.project.ibtss.model.TicketPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketPayRepository extends JpaRepository<TicketPay, Integer> {
    List<TicketPay> findAllByPaymentId(int paymentId);
    TicketPay findByTicketId(int ticketId);
}
