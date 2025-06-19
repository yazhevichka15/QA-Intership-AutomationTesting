package models.searchEvents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Datum {
    @JsonProperty("EventId")
    public int eventId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("EventDate")
    public Date eventDate;
    @JsonProperty("SportId")
    public int sportId;
    @JsonProperty("SportName")
    public String sportName;
    @JsonProperty("SportTypeId")
    public int sportTypeId;
    @JsonProperty("CategoryName")
    public String categoryName;
    @JsonProperty("ChampName")
    public String champName;
    @JsonProperty("IsLive")
    public boolean isLive;
}
