package HighlightsManager;

import ConfigReader.ConfigReader;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import models.SearchEvents.SearchEventsRequest;
import models.UpdateConfig.*;

import java.util.ArrayList;

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
    public static UpdateConfigRequest createEmptyRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        return requestBody;
    }

    @Step("Create UpdateConfig request body to add new language")
    public static UpdateConfigRequest createAddLanguageRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        LanguageTab languageTab0 = new LanguageTab();
        languageTab0.languageId = 111;
        languageTab0.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs.add(languageTab0);

        Sport sport0 = new Sport();
        sport0.sportId = 99;
        sport0.order = 1;
        sport0.name = "Archery";
        sport0.isEnabled = true;
        sport0.count = 0;
        sport0.categories = new ArrayList<>();
        requestBody.sports.add(sport0);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to delete the language")
    public static UpdateConfigRequest createDeleteLanguageRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        Sport sport0 = new Sport();
        sport0.sportId = 99;
        sport0.order = 1;
        sport0.name = "Archery";
        sport0.isEnabled = true;
        sport0.count = 0;
        sport0.categories = new ArrayList<>();
        requestBody.sports.add(sport0);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to add new event to language")
    public static UpdateConfigRequest createAddEventRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        LanguageTab languageTab0 = new LanguageTab();
        languageTab0.languageId = 66;
        languageTab0.highlightsEvents = new ArrayList<>();

        HighlightsEvent event0 = new HighlightsEvent();
        event0.eventId = 10383907;
        event0.order = 1;
        event0.isPromo = false;
        event0.isSafe = false;
        languageTab0.highlightsEvents.add(event0);

        requestBody.languageTabs.add(languageTab0);

        Sport sport0 = new Sport();
        sport0.sportId = 105;
        sport0.order = 1;
        sport0.name = "3x3 Basketball";
        sport0.isEnabled = true;
        sport0.categories = new ArrayList<>();
        sport0.count = 1;
        requestBody.sports.add(sport0);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to set IsSafe status to the event")
    public static UpdateConfigRequest createSetIsSafeRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        HighlightsEvent event0 = new HighlightsEvent();
        event0.eventId = 10299752;
        event0.order = 1;
        event0.isPromo = false;
        event0.isSafe = true;
        requestBody.highlightsEvents.add(event0);

        Sport sport0 = new Sport();
        sport0.sportId = 106;
        sport0.order = 1;
        sport0.name = "Aussie Rules";
        sport0.isEnabled = true;
        sport0.count = 2;
        sport0.categories = new ArrayList<>();

        Category category0 = new Category();
        category0.categoryId = 1256;
        category0.name = "Australia";
        sport0.categories.add(category0);

        requestBody.sports.add(sport0);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to set IsPromo status to the event")
    public static UpdateConfigRequest createSetIsPromoRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        HighlightsEvent event0 = new HighlightsEvent();
        event0.eventId = 10299752;
        event0.order = 1;
        event0.isPromo = true;
        event0.isSafe = false;
        requestBody.highlightsEvents.add(event0);

        Sport sport0 = new Sport();
        sport0.sportId = 106;
        sport0.order = 1;
        sport0.name = "Aussie Rules";
        sport0.isEnabled = true;
        sport0.count = 2;
        sport0.categories = new ArrayList<>();

        Category category0 = new Category();
        category0.categoryId = 1256;
        category0.name = "Australia";
        sport0.categories.add(category0);

        requestBody.sports.add(sport0);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to set the IsSafe and IsPromo statuses to the event")
    public static UpdateConfigRequest createDoubleStatusRequest() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        HighlightsEvent event0 = new HighlightsEvent();
        event0.eventId = 10299752;
        event0.order = 1;
        event0.isPromo = true;
        event0.isSafe = true;
        requestBody.highlightsEvents.add(event0);

        Sport sport0 = new Sport();
        sport0.sportId = 106;
        sport0.order = 1;
        sport0.name = "Aussie Rules";
        sport0.isEnabled = true;
        sport0.count = 2;
        sport0.categories = new ArrayList<>();

        Category category0 = new Category();
        category0.categoryId = 1256;
        category0.name = "Australia";
        sport0.categories.add(category0);

        requestBody.sports.add(sport0);

        return requestBody;
    }

    @Step("Create UpdateConfig request body to search events with incorrect dates")
    public static SearchEventsRequest createSearchEventsRequest() {
        SearchEventsRequest requestBody = new SearchEventsRequest();
        requestBody.champId = 50320;

        requestBody.dateFrom = "2025-06-20 12:00:00";
        requestBody.dateTo = "2025-06-20 00:00:00";

        requestBody.sportIds = new ArrayList<>();
        Integer sportId0 = 106;
        requestBody.sportIds.add(sportId0);

        return requestBody;
    }
}
