package models.updateConfig;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UpdateConfigRequest {
    public int configId;
    public ArrayList<HighlightsEvent> highlightsEvents;
    public ArrayList<LanguageTab> languageTabs;
    public ArrayList<Sport> sports;
}
