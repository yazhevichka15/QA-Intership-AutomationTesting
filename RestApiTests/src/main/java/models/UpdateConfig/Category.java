package models.UpdateConfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Category {
    @JsonProperty("CategoryId")
    public int categoryId;

    @JsonProperty("Name")
    public String name;
}
