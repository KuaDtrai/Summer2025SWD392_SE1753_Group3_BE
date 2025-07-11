package com.project.ibtss.model;

import com.project.ibtss.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", unique = true)
    Account account;
    
    Gender gender;
    LocalDate dob;

    @Column(length = 50)
    String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    List<Feedback> feedbackList;
}

