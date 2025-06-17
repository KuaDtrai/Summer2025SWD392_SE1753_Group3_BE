package com.project.ibtss.model;

import com.project.ibtss.enums.BusType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    BusType busType;
    Boolean status;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
