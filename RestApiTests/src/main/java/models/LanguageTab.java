package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class LanguageTab {
    @JsonProperty("LanguageId")
    public int languageId;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("TopEvents")
    public ArrayList<Object> topEvents;
}
