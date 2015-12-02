package hello.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by orbot on 02.12.15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    @Size(min = 4, max = 30)
    String username;
    @NotNull
    @Size(min = 4, max = 100)
    String password;
}
