package com.project.ibtss.model;

import com.project.ibtss.enums.StationStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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
    String name;
    String address;
    LocalDateTime createdDate;

    @Column(nullable = true)
    LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    StationStatus status;
}