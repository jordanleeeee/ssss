package cd1.ssss.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Jordan
 */
public class RecoverTextSecretResponse {
    @NotNull
    @NotBlank
    @JsonProperty("secret")
    public String secret;
}
