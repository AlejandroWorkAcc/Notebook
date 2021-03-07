package ru.dolinini.notebook.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.READNOTES,
                Permission.WRITENOTES)),
    DEVELOPER(Set.of(Permission.READ,
                     Permission.WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                                    .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                                    .collect(Collectors.toSet());
    }
}
