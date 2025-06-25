package HighlightsManager;

import clients.*;

import io.restassured.response.Response;
import com.altenar.sb2.admin.model.HighlightsConfigSettingsApiResult;
import com.altenar.sb2.admin.model.UpdateHighlightsConfigRequest;
import com.altenar.sb2.admin.model.ApiResult;
import com.altenar.sb2.admin.model.SearchHighlightsEventsRequest;
import com.altenar.sb2.admin.model.EventCandidateItemListApiResult;
import com.altenar.sb2.frontend.model.TopSportFullModelOutIEnumerableApiResult;
import static HighlightsManager.HighlightManagerSteps.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HighlightManagerTests {
    private final String baseAdminURI = "https://sb2admin-altenar2-stage.biahosted.com";
    private final String baseFrontendURI = "https://sb2frontend-altenar2-stage.biahosted.com";
    private Cookies authCookies;

    @BeforeEach
    @Step("Get Highlight Manager login cookies")
    void setUp() {
        authCookies = getCookies(baseAdminURI);
    }

    @Test
    @DisplayName("Adding a language should increase the number of languages in the languageTab")
    void testAddLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createAddLanguageRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("Verify languageTab update after adding a new language",
                () -> assertThat("The languageTab should be empty before adding a new language",
                        configSettingsBefore.getData().getLanguageTabs(), hasSize(0)),

                () -> assertThat("The configuration update should be successful",
                        updateConfigResponse.getSuccess(), is(true)),

                () -> assertThat("The number of languages in the languageTab should increase",
                        configSettingsAfter.getData().getLanguageTabs(), hasSize(1))
        );
    }

    @Test
    @DisplayName("Deleting the language should reduce the number of languages in the languageTab")
    void testDeleteLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createDeleteLanguageRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("Verify languageTab update after deleting the language",
                () -> assertThat("The languageTab should contain the language before deleting",
                        configSettingsBefore.getData().getLanguageTabs(), hasSize(1)),

                () -> assertThat("The configuration update should be successful",
                        updateConfigResponse.getSuccess(), is(true)),

                () -> assertThat("The languageTab should be empty after deleting the language",
                        configSettingsAfter.getData().getLanguageTabs(), hasSize(0))
        );
    }

    @Test
    @DisplayName("Adding a event to language should increase the number of events in the topEvents")
    void testAddEventToLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createAddEventRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("Verify topEvents update after adding a new event to language",
                () -> assertThat("The topEvents should be empty before adding a new event",
                        configSettingsBefore.getData().getLanguageTabs().getFirst().getTopEvents(), hasSize(0)),

                () -> assertThat("The configuration update should be successful",
                        updateConfigResponse.getSuccess(), is(true)),

                () -> assertThat("The number of language's events in the topEvents should increase",
                        configSettingsAfter.getData().getLanguageTabs().getFirst().getTopEvents(), hasSize(1))
        );
    }

    @Test
    @DisplayName("Searching events with incorrect dates should get a server error")
    void testSearchEvents() {
        SearchHighlightsEventsRequest requestBody = createSearchEventsRequest();
        EventCandidateItemListApiResult searchEvents =
                BackOfficeClient.searchEvents(baseAdminURI, requestBody, authCookies);

        assertThat("Search with an invalid request should get an server error 400",
                searchEvents.getSuccess(), is(false));
    }

    @Test
    @DisplayName("Adding IsSafe status to the event should update the isSafe parameter to true")
    void testSetEventStatusIsSafe() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createSetIsSafeRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("Verify IsSafe status update after set it to the event",
                () -> assertThat("The isSafe parameter should be false before update",
                        configSettingsBefore.getData().getEvents().getFirst().getIsSafe(), is(false)),

                () -> assertThat("The configuration update should be successful",
                        updateConfigResponse.getSuccess(), is(true)),

                () -> assertThat("The isSafe parameter should be true after update",
                        configSettingsAfter.getData().getEvents().getFirst().getIsSafe(), is(true))
        );
    }

    @Test
    @DisplayName("Adding IsPromo status to the event should update the isPromo parameter to true")
    void testSetEventStatusIsPromo() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createSetIsPromoRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("Verify IsPromo status update after set it to the event",
                () -> assertThat("The isPromo parameter should be false before update",
                        configSettingsBefore.getData().getEvents().getFirst().getIsPromo(), is(false)),

                () -> assertThat("The configuration update should be successful",
                        updateConfigResponse.getSuccess(), is(true)),

                () -> assertThat("The isPromo parameter should be true after update",
                        configSettingsAfter.getData().getEvents().getFirst().getIsPromo(), is(true))
        );
    }

    @Test
    @DisplayName("Adding IsPromo status to event when IsSafe status added should get a server error")
    void testSetEventStatusIsPromoWhenIsSafe() {
        UpdateHighlightsConfigRequest requestBody = createDoubleStatusRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        assertThat("Setting two statuses at once should get an server error 400",
                updateConfigResponse.getSuccess(), is(false));
    }

    @Test
    @DisplayName("Deleting all sports from Top Sports should get a server error")
    void testDeleteAllSports() {
        UpdateHighlightsConfigRequest requestBody = createEmptyRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(baseAdminURI, requestBody, authCookies);

        assertThat("Deleting all sports at once should get an server error 400",
                updateConfigResponse.getSuccess(), is(false));
    }

    @Test
    @DisplayName("Should return top sports for type upcoming with expected structure and content")
    void testGetTopSportsUpcoming() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("timezoneOffset", "-180");
        queryParams.put("langId", "66");
        queryParams.put("skinName", "betsonic");
        queryParams.put("configId", "1");
        queryParams.put("culture", "no-no");
        queryParams.put("countryCode", "RU");
        queryParams.put("deviceType", "Desktop");
        queryParams.put("numformat", "en");
        queryParams.put("integration", "skintest");
        queryParams.put("topSportType", "upcoming");

        TopSportFullModelOutIEnumerableApiResult topSports =
                FrontEndClient.getTopSports(baseFrontendURI, queryParams);

        assertAll("Validate top sports response for upcoming type",
                () -> assertThat("Should return 2 top sports",
                        topSports.getResult(), hasSize(2)),

                () -> assertThat("First sport should have sportId equal to 106",
                        topSports.getResult().getFirst().getSportId(), is(106)),

                () -> assertThat("Second sport should have sportId equal to -1",
                        topSports.getResult().getLast().getSportId(), is(-1))
        );
    }
}
