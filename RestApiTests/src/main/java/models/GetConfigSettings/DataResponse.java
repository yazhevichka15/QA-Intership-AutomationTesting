package models.GetConfigSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DataResponse {
    @JsonProperty("Sports")
    public ArrayList<Sport> sports;

    @JsonProperty("Events")
    public ArrayList<Event> events;

    @JsonProperty("LanguageTabs")
    public ArrayList<LanguageTab> languageTabs;
}
