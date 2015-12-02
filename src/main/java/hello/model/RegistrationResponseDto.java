package hello.model;

import lombok.Data;
import org.springframework.validation.BindingResult;

/**
 * Created by orbot on 02.12.15.
 */
@Data
public class RegistrationResponseDto {
    private boolean success;
    private BindingResult fieldErrors;
    String token;
}
