package com.project.ibtss.model;

import com.project.ibtss.enums.StationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(length = 50)
    String name;

    @Column(length = 50)
    String address;
    LocalDateTime createdDate;

    @Column(nullable = true)
    LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    StationStatus status;
}