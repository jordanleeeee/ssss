package cd1.ssss.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Jordan
 */
public class CreateShareRequest {
    @Min(1)
    @NotNull
    @JsonProperty("numberOfShares")
    public Integer numberOfShares;

    @Min(1)
    @NotNull
    @JsonProperty("threshold")
    public Integer threshold;

    @Min(0)
    @NotNull
    @JsonProperty("secret")
    public Integer secret;
}
