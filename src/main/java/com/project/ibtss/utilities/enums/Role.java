package com.project.ibtss.utilities.enums;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN", Set.of(
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_READ,
            Permission.ADMIN_DELETE
    )),
    CUSTOMER("CUSTOMER", Set.of(
            Permission.CUSTOMER_CREATE,
            Permission.CUSTOMER_UPDATE,
            Permission.CUSTOMER_READ
    )),

    STAFF("STAFF", Set.of(
            Permission.STAFF_READ,
            Permission.STAFF_CREATE,
            Permission.STAFF_UPDATE,
            Permission.STAFF_DELETE
    ));

    String roleName;
    Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<>(
                getPermissions().stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .toList()
        );

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
