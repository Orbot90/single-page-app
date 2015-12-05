package hello.model;

import lombok.Data;

/**
 * Created by orbot on 02.12.15.
 */
@Data
public class RegistrationResponseDto {
    private boolean success;
    String token;
}
