package cd1.ssss.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Jordan
 */
public class RecoverSecretResponse {
    @NotNull
    @Min(0)
    @JsonProperty("secret")
    public Integer secret;
}
