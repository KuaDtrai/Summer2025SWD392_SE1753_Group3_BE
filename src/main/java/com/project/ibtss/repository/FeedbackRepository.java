package com.project.ibtss.repository;

import com.project.ibtss.model.Customer;
import com.project.ibtss.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    List<Feedback> getAllByCustomer(Customer customer);
}
