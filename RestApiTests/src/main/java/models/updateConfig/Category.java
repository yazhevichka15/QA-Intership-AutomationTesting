package models.updateConfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data

public class Category {
    @JsonProperty("CategoryId")
    public int categoryId;

    @JsonProperty("Name")
    public String name;


}
