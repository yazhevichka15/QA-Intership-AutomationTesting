package models.getConfigSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sport {
    @JsonProperty("SportId")
    public int sportId;

    @JsonProperty("Order")
    public int order;

    @JsonProperty("IsEnabled")
    public boolean isEnabled;
}
