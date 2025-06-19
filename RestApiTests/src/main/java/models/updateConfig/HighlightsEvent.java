package models.updateConfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HighlightsEvent {
    @JsonProperty("EventId")
    public int eventId;
    @JsonProperty("Order")
    public int order;
    @JsonProperty("IsPromo")
    public boolean isPromo;
    @JsonProperty("IsSafe")
    public boolean isSafe;
}
