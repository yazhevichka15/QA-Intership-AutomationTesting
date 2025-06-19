package models.updateConfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Sport {
    @JsonProperty("SportId")
    public int sportId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Count")
    public int count;
    @JsonProperty("IsEnabled")
    public boolean isEnabled;
    @JsonProperty("Order")
    public int order;
    @JsonProperty("Categories")
    public ArrayList<Category> categories;
}
