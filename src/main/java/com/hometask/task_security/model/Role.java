package com.hometask.task_security.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Authorization.EDIT)),
    ADMIN(Set.of(Authorization.READ, Authorization.EDIT, Authorization.DELETE));

    Role(Set<Authorization> authorization) {
        this.authorization = authorization;
    }

    @Getter
    private final Set<Authorization> authorization;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getAuthorization().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getAuthorization()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
