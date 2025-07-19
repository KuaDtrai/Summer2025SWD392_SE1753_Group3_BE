package com.project.ibtss.model;

import com.project.ibtss.utilities.enums.Position;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", unique = true)
    Account account;

    Position position;
    LocalDate hiredDate;
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    List<Feedback> receivedFeedbacks;

    @OneToMany(mappedBy = "staffReply", fetch = FetchType.LAZY)
    List<Feedback> repliedFeedbacks;
}

