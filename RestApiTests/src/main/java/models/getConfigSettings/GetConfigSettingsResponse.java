package models.getConfigSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetConfigSettingsResponse {
    @JsonProperty("Data")
    public DataResponse data;

    @JsonProperty("Success")
    public boolean success;

    @JsonProperty("Error")
    public Object error;
}
