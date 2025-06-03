package com.project.ibtss.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`token`")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String value;

    String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`account_id`")
    Account account;

    String status;
}

