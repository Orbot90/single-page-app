package hello.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by orbot on 21.11.15.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NonNull
    @Size(min = 4, max=30)
    private String username;
    @NonNull
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !accountDisabled;
    }
}
