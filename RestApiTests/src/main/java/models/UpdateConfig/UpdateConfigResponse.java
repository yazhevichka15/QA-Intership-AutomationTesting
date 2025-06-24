package models.UpdateConfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateConfigResponse {
    @JsonProperty("Success")
    public boolean success;

    @JsonProperty("Error")
    public Object error;
}
