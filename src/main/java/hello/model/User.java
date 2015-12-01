package hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by orbot on 21.11.15.
 */
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Size(min = 4, max=30)
    private String username;
    @NotNull
    @Size(min=4, max=100)
    private String password;

    @Transient
    private Long expires;

    @NotNull
    private boolean accountExpired;
    @NotNull
    private boolean accountLocked;
    @NotNull
    private boolean credentialsExpired;
    @NotNull
    private boolean accountDisabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserAuthority> authorities;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public User(String username, Date expires) {
        this.username = username;
        this.expires = expires.getTime();
    }

    public Set<UserRole> getRoles() {
        Set<UserRole> userRoles = EnumSet.noneOf(UserRole.class);

        if(authorities != null) {
            for (UserAuthority authority : authorities) {
                userRoles.add(UserRole.valueOf(authority));
            }
        }
        return userRoles;
    }

    public void grantRole(UserRole role) {
        if(authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(role.asAuthorityFor(this));
    }

    public boolean hasRole(UserRole role) {
        return authorities.contains(role.asAuthorityFor(this));
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !accountDisabled;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
