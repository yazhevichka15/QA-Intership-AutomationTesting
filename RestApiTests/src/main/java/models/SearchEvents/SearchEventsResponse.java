package models.SearchEvents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SearchEventsResponse {
    @JsonProperty("Data")
    public ArrayList<Datum> data;

    @JsonProperty("Success")
    public boolean success;

    @JsonProperty("Error")
    public Object error;
}
