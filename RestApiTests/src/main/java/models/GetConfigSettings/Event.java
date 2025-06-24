package models.GetConfigSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Event {
    @JsonProperty("EventId")
    public int eventId;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("EventDate")
    public Date eventDate;

    @JsonProperty("SportId")
    public int sportId;

    @JsonProperty("Sport")
    public String sport;

    @JsonProperty("Category")
    public String category;

    @JsonProperty("Championship")
    public String championship;

    @JsonProperty("Order")
    public int order;

    @JsonProperty("IsPromo")
    public boolean isPromo;

    @JsonProperty("IsSafe")
    public boolean isSafe;
}
