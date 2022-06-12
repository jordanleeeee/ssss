package cd1.ssss.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jordan
 */
public class CreateShareResponse {
    @NotNull
    @NotEmpty
    @JsonProperty("shares")
    public List<String> shares;
}
