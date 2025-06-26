package HighlightsManager;

import com.altenar.sb2.admin.model.UpdateHighlightsConfigRequest;
import com.altenar.sb2.admin.model.LanguageTabRequestItem;
import com.altenar.sb2.admin.model.SportRequestItem;
import com.altenar.sb2.admin.model.HighlightsEventRequestItem;
import com.altenar.sb2.admin.model.SearchHighlightsEventsRequest;

import io.qameta.allure.Step;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class HighlightManagerSteps {
    private static final int CONFIG_ID = 126;
    private static final int NORWEGIAN_LANGUAGE_ID = 66;
    private static final int BEACH_VOLLEY_SPORT_ID = 82;
    private static final int TEST_CHAMP_ID = 18828;
    private static final int ORDER = 1;

    private static final long TEST_EVENT_ID = 10439428;

    private static final String DATE_FROM = "2025-06-26 23:00:00";
    private static final String DATE_TO = "2025-06-26 12:00:00";

    @Step("Create empty UpdateConfig request body")
    public static UpdateHighlightsConfigRequest createEmptyRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(new ArrayList<>());
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(new ArrayList<>());

        return requestBody;
    }

    @Step("Create UpdateConfig request body to add new language")
    public static UpdateHighlightsConfigRequest createAddLanguageRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        ArrayList<LanguageTabRequestItem> languageTabs = new ArrayList<>();
        ArrayList<SportRequestItem> sports = new ArrayList<>();

        LanguageTabRequestItem languageTab0 = new LanguageTabRequestItem();
        languageTab0.setLanguageId(NORWEGIAN_LANGUAGE_ID);
        languageTab0.setHighlightsEvents(new ArrayList<>());
        languageTabs.add(languageTab0);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(BEACH_VOLLEY_SPORT_ID);
        sport0.setOrder(ORDER);
        sport0.setIsEnabled(true);
        sports.add(sport0);

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(new ArrayList<>());
        requestBody.setLanguageTabs(languageTabs);
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to delete the language")
    public static UpdateHighlightsConfigRequest createDeleteLanguageRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        ArrayList<SportRequestItem> sports = new ArrayList<>();

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(BEACH_VOLLEY_SPORT_ID);
        sport0.setOrder(ORDER);
        sport0.setIsEnabled(true);
        sports.add(sport0);

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(new ArrayList<>());
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to add new event to language")
    public static UpdateHighlightsConfigRequest createAddEventToLangRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        ArrayList<LanguageTabRequestItem> languageTabs = new ArrayList<>();
        ArrayList<SportRequestItem> sports = new ArrayList<>();

        LanguageTabRequestItem languageTab0 = new LanguageTabRequestItem();

        ArrayList<HighlightsEventRequestItem> langHighlightsEvents = new ArrayList<>();
        HighlightsEventRequestItem highlightsEvent0 = new HighlightsEventRequestItem();
        highlightsEvent0.setEventId(TEST_EVENT_ID);
        highlightsEvent0.setOrder(ORDER);
        highlightsEvent0.setIsPromo(false);
        highlightsEvent0.setIsSafe(false);
        langHighlightsEvents.add(highlightsEvent0);

        languageTab0.setLanguageId(NORWEGIAN_LANGUAGE_ID);
        languageTab0.setHighlightsEvents(langHighlightsEvents);
        languageTabs.add(languageTab0);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(BEACH_VOLLEY_SPORT_ID);
        sport0.setOrder(ORDER);
        sport0.setIsEnabled(true);
        sports.add(sport0);

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(new ArrayList<>());
        requestBody.setLanguageTabs(languageTabs);
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to set IsSafe status to the event")
    public static UpdateHighlightsConfigRequest createSetIsSafeRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        ArrayList<SportRequestItem> sports = new ArrayList<>();
        ArrayList<HighlightsEventRequestItem> highlightsEvents = new ArrayList<>();

        HighlightsEventRequestItem highlightsEvent0 = new HighlightsEventRequestItem();
        highlightsEvent0.setEventId(TEST_EVENT_ID);
        highlightsEvent0.setOrder(ORDER);
        highlightsEvent0.setIsSafe(true);
        highlightsEvent0.setIsPromo(false);
        highlightsEvents.add(highlightsEvent0);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(BEACH_VOLLEY_SPORT_ID);
        sport0.setOrder(ORDER);
        sport0.setIsEnabled(true);
        sports.add(sport0);

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(highlightsEvents);
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to set IsPromo status to the event")
    public static UpdateHighlightsConfigRequest createSetIsPromoRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        ArrayList<SportRequestItem> sports = new ArrayList<>();
        ArrayList<HighlightsEventRequestItem> highlightsEvents = new ArrayList<>();

        HighlightsEventRequestItem highlightsEvent0 = new HighlightsEventRequestItem();
        highlightsEvent0.setEventId(TEST_EVENT_ID);
        highlightsEvent0.setOrder(ORDER);
        highlightsEvent0.setIsSafe(false);
        highlightsEvent0.setIsPromo(true);
        highlightsEvents.add(highlightsEvent0);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(BEACH_VOLLEY_SPORT_ID);
        sport0.setOrder(ORDER);
        sport0.setIsEnabled(true);
        sports.add(sport0);

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(highlightsEvents);
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to set the IsSafe and IsPromo statuses to the event")
    public static UpdateHighlightsConfigRequest createDoubleStatusRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        ArrayList<SportRequestItem> sports = new ArrayList<>();
        ArrayList<HighlightsEventRequestItem> highlightsEvents = new ArrayList<>();

        HighlightsEventRequestItem highlightsEvent0 = new HighlightsEventRequestItem();
        highlightsEvent0.setEventId(TEST_EVENT_ID);
        highlightsEvent0.setOrder(ORDER);
        highlightsEvent0.setIsSafe(true);
        highlightsEvent0.setIsPromo(true);
        highlightsEvents.add(highlightsEvent0);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(BEACH_VOLLEY_SPORT_ID);
        sport0.setOrder(ORDER);
        sport0.setIsEnabled(true);
        sports.add(sport0);

        requestBody.setConfigId(CONFIG_ID);
        requestBody.setHighlightsEvents(highlightsEvents);
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to search events with incorrect dates")
    public static SearchHighlightsEventsRequest createInvalidSearchEventsRequest() {
        SearchHighlightsEventsRequest requestBody = new SearchHighlightsEventsRequest();

        List<Integer> sportIds = new ArrayList<>();
        sportIds.add(BEACH_VOLLEY_SPORT_ID);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime from = formatter.parseDateTime(DATE_FROM);
        DateTime to = formatter.parseDateTime(DATE_TO);

        requestBody.setChampId(TEST_CHAMP_ID);
        requestBody.setSportIds(sportIds);
        requestBody.setDateFrom(from);
        requestBody.setDateTo(to);

        return requestBody;
    }
}
