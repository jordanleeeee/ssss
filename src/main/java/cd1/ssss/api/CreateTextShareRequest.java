package cd1.ssss.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Jordan
 */
public class CreateTextShareRequest {
    @Min(1)
    @NotNull
    @JsonProperty("numberOfShares")
    public Integer numberOfShares;

    @Min(1)
    @NotNull
    @JsonProperty("threshold")
    public Integer threshold;

    @NotNull
    @NotBlank(message = "secret must not be empty")
    @JsonProperty("secret")
    public String secret;
}
