package com.project.ibtss.model;

import com.project.ibtss.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String email;
    String passwordHash;
    Role role; // customer, staff
    String fullName;
    String phone;
    Boolean isActive;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    Customer customer;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    Staff staff;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    List<Tickets> tickets;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    List<Trips> tripsAsDriver;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    List<Token> tokens;
}
