package HighlightsManager;

import clients.*;
import com.altenar.sb2.admin.model.HighlightsConfigSettingsApiResult;
import com.altenar.sb2.admin.model.UpdateHighlightsConfigRequest;
import com.altenar.sb2.admin.model.ApiResult;
import com.altenar.sb2.admin.model.SearchHighlightsEventsRequest;
import com.altenar.sb2.admin.model.EventCandidateItemListApiResult;
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

public class BackOfficeTests {
    private Cookies authCookies;
    private static final String CONFIG_ID = "126";

    @BeforeEach
    @Step("Get BO Highlight Manager login cookies")
    void setUp() {
        authCookies = BackOfficeClient.getCookies();
    }

    @Test
    @DisplayName("Adding a language should increase the number of languages in the languageTab")
    void testAddLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", CONFIG_ID);

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createAddLanguageRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

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
        queryParams.put("configId", CONFIG_ID);

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createDeleteLanguageRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

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
        queryParams.put("configId", CONFIG_ID);

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createAddEventToLangRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

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
    @DisplayName("Searching events with incorrect dates shouldn't be successful")
    void testSearchEventsWithInvalidDates() {
        SearchHighlightsEventsRequest requestBody = createInvalidSearchEventsRequest();
        EventCandidateItemListApiResult searchEvents =
                BackOfficeClient.searchEvents(requestBody, authCookies);

        assertThat("Search with an invalid request shouldn't be successful",
                searchEvents.getSuccess(), is(false));
    }

    @Test
    @DisplayName("Adding IsSafe status to the event should update the isSafe parameter to true")
    void testSetEventStatusIsSafe() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", CONFIG_ID);

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createSetIsSafeRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

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
        queryParams.put("configId", CONFIG_ID);

        HighlightsConfigSettingsApiResult configSettingsBefore =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

        UpdateHighlightsConfigRequest requestBody = createSetIsPromoRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        HighlightsConfigSettingsApiResult configSettingsAfter =
                BackOfficeClient.getConfigSettings(queryParams, authCookies);

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
    @DisplayName("Adding IsPromo status to event when IsSafe status added shouldn't be successful")
    void testSetDoubleStatus() {
        UpdateHighlightsConfigRequest requestBody = createDoubleStatusRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        assertThat("Setting two statuses at once shouldn't be successful",
                updateConfigResponse.getSuccess(), is(false));
    }

    @Test
    @DisplayName("Deleting all sports from Top Sports shouldn't be successful")
    void testDeleteAllSports() {
        UpdateHighlightsConfigRequest requestBody = createEmptyRequest();
        ApiResult updateConfigResponse =
                BackOfficeClient.updateConfig(requestBody, authCookies);

        assertThat("Deleting all sports at once shouldn't be successful",
                updateConfigResponse.getSuccess(), is(false));
    }
}
