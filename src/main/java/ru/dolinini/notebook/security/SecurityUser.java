package ru.dolinini.notebook.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.dolinini.notebook.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final boolean isActive;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String username, String password, boolean isActive, List<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityUser that = (SecurityUser) o;
        return isActive == that.isActive &&
                username.equals(that.username) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isActive);
    }

    public static UserDetails getUserDetailsFromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getFirstname(),
                user.getPassword(),
                Status.ACTIVE.equals(user.getStatus()),
                Status.ACTIVE.equals(user.getStatus()),
                Status.ACTIVE.equals(user.getStatus()),
                Status.ACTIVE.equals(user.getStatus()),
                user.getRole().getAuthorities()
        );
    }

}
