package com.project.ibtss.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Buses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String licensePlate;
    Integer seatCount;
    String busType;
}
