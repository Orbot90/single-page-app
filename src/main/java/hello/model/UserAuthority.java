package hello.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by orbot on 30.11.15.
 */
@Entity
@IdClass(UserAuthority.class)
@Data
@EqualsAndHashCode(of = "authority")
@ToString(of = "authority")
public class UserAuthority implements GrantedAuthority {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Id
    private User user;

    @NotNull
    @Id
    private String authority;
}
