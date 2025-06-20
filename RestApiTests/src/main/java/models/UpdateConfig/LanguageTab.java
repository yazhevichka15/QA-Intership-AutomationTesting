package models.UpdateConfig;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LanguageTab {
    public int languageId;
    public ArrayList<HighlightsEvent> highlightsEvents;
}
