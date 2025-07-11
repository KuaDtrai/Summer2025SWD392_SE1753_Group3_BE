package com.project.ibtss.repository;

import com.project.ibtss.model.SubPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubPayRepository extends JpaRepository<SubPay, Integer> {
    SubPay findByPaymentId(Integer paymentId);
}
