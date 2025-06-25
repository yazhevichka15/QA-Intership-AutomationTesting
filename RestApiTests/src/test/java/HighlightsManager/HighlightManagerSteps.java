package HighlightsManager;

import com.altenar.sb2.admin.model.UpdateHighlightsConfigRequest;
import com.altenar.sb2.admin.model.LanguageTabRequestItem;
import com.altenar.sb2.admin.model.SportRequestItem;
import com.altenar.sb2.admin.model.HighlightsEventRequestItem;
import com.altenar.sb2.admin.model.SearchHighlightsEventsRequest;

import ConfigReader.ConfigReader;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HighlightManagerSteps {
    private final static ConfigReader configReader = new ConfigReader();

    @Step("Receive Highlight Manager login cookies")
    public static Cookies getCookies(String baseAdminURI) {
        return given()
                .baseUri(baseAdminURI)
                .param("UserName", configReader.getUsername())
                .param("Password", configReader.getPassword())
                .when()
                .post("/Account/Login")
                .then()
                .extract()
                .response()
                .getDetailedCookies();
    }

    @Step("Create empty UpdateConfig request body")
    public static UpdateHighlightsConfigRequest createEmptyRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        requestBody.setConfigId(126);

        requestBody.setHighlightsEvents(new ArrayList<>());
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(new ArrayList<>());

        return requestBody;
    }

    @Step("Create UpdateConfig request body to add new language")
    public static UpdateHighlightsConfigRequest createAddLanguageRequest() {
        ArrayList<LanguageTabRequestItem> languageTabs = new ArrayList<>();
        ArrayList<SportRequestItem> sports = new ArrayList<>();

        LanguageTabRequestItem languageTab0 = new LanguageTabRequestItem();
        languageTab0.setLanguageId(111);
        languageTab0.setHighlightsEvents(new ArrayList<>());

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(99);
        sport0.setOrder(1);
        sport0.setIsEnabled(true);

        languageTabs.add(languageTab0);
        sports.add(sport0);

        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();
        requestBody.setConfigId(126);
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
        sport0.setSportId(99);
        sport0.setOrder(1);
        sport0.setIsEnabled(true);

        sports.add(sport0);

        requestBody.setConfigId(126);
        requestBody.setHighlightsEvents(new ArrayList<>());
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to add new event to language")
    public static UpdateHighlightsConfigRequest createAddEventRequest() {
        UpdateHighlightsConfigRequest requestBody = new UpdateHighlightsConfigRequest();

        ArrayList<LanguageTabRequestItem> languageTabs = new ArrayList<>();
        ArrayList<SportRequestItem> sports = new ArrayList<>();
        ArrayList<HighlightsEventRequestItem> highlightsEvents = new ArrayList<>();

        LanguageTabRequestItem languageTab0 = new LanguageTabRequestItem();
        languageTab0.setLanguageId(66);
        languageTab0.setHighlightsEvents(new ArrayList<>());

        HighlightsEventRequestItem highlightsEvent0 = new HighlightsEventRequestItem();
        highlightsEvent0.setEventId((long)10383907);
        highlightsEvent0.setOrder(1);
        highlightsEvent0.setIsPromo(false);
        highlightsEvent0.setIsSafe(false);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(105);
        sport0.setOrder(1);
        sport0.setIsEnabled(true);

        languageTabs.add(languageTab0);
        sports.add(sport0);
        highlightsEvents.add(highlightsEvent0);

        requestBody.setConfigId(126);
        requestBody.setHighlightsEvents(highlightsEvents);
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
        highlightsEvent0.setEventId((long)10299752);
        highlightsEvent0.setOrder(1);
        highlightsEvent0.setIsSafe(false);
        highlightsEvent0.setIsPromo(false);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(106);
        sport0.setOrder(1);
        sport0.setIsEnabled(true);

        sports.add(sport0);
        highlightsEvents.add(highlightsEvent0);

        requestBody.setConfigId(126);
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
        highlightsEvent0.setEventId((long)10299752);
        highlightsEvent0.setOrder(1);
        highlightsEvent0.setIsSafe(false);
        highlightsEvent0.setIsPromo(true);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(106);
        sport0.setOrder(1);
        sport0.setIsEnabled(true);

        sports.add(sport0);
        highlightsEvents.add(highlightsEvent0);

        requestBody.setConfigId(126);
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
        highlightsEvent0.setEventId((long)10299752);
        highlightsEvent0.setOrder(1);
        highlightsEvent0.setIsSafe(true);
        highlightsEvent0.setIsPromo(true);

        SportRequestItem sport0 = new SportRequestItem();
        sport0.setSportId(106);
        sport0.setOrder(1);
        sport0.setIsEnabled(true);

        sports.add(sport0);
        highlightsEvents.add(highlightsEvent0);

        requestBody.setConfigId(126);
        requestBody.setHighlightsEvents(highlightsEvents);
        requestBody.setLanguageTabs(new ArrayList<>());
        requestBody.setSports(sports);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to search events with incorrect dates")
    public static SearchHighlightsEventsRequest createSearchEventsRequest() {
        SearchHighlightsEventsRequest requestBody = new SearchHighlightsEventsRequest();

        List<Integer> sportIds = new ArrayList<>();

        Integer sportId0 = 106;

        sportIds.add(sportId0);

        requestBody.setChampId(50320);
        requestBody.setSearchQuery("");
        requestBody.setSportIds(sportIds);
        requestBody.setDateFrom(new DateTime("2025-06-20 12:00:00"));
        requestBody.setDateTo(new DateTime("2025-06-20 00:00:00"));

        return requestBody;
    }
}
