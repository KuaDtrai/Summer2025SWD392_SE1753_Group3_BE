package com.project.ibtss.enums;

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

    )),

    STAFF("STAFF", Set.of())

    ;

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
