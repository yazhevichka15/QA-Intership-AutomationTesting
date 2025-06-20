package HighlightsManager;

import clients.*;

import io.restassured.response.Response;
import models.getConfigSettings.*;
import models.getTopSports.GetTopSportsResponse;
import models.searchEvents.SearchEventsRequest;
import models.updateConfig.UpdateConfigRequest;
import static HighlightsManager.HighlightManagerSteps.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HighlightManagerTests {
    private final String baseAdminURI = "https://sb2admin-altenar2-stage.biahosted.com";
    private final String baseFrontendURI = "https://sb2frontend-altenar2-stage.biahosted.com";
    private Cookies authCookies;

    @BeforeEach
    @Step("")
    void setUp() {
        authCookies = getCookies(baseAdminURI);
    }

//    @Test
//    @DisplayName("")
//    void testAddLanguage() {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("configId", "126");
//
//        GetConfigSettingsResponse getConfigSettingsResponseBeforeAdd =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        UpdateConfigRequest updateRequestBody = setRequestBodyToAddLanguage();
//        Response updateConfigResponse = BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);
//
//        GetConfigSettingsResponse getConfigSettingsResponseAfterAdd =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        assertAll("",
//                () -> assertThat("",
//                        getConfigSettingsResponseBeforeAdd.data.languageTabs.size(), is(0)),
//                () -> assertThat("",
//                        updateConfigResponse.statusCode(), is(200)),
//                () -> assertThat("",
//                        getConfigSettingsResponseAfterAdd.data.languageTabs.size(), is(1))
//        );
//    }

//    @Test
//    @DisplayName("")
//    void testDeleteLanguage() {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("configId", "126");
//
//        GetConfigSettingsResponse getConfigSettingsResponseBeforeDelete =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        UpdateConfigRequest updateRequestBody = setRequestBodyToDeleteLanguage();
//        Response updateConfigResponse = BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);
//
//        GetConfigSettingsResponse getConfigSettingsResponseAfterDelete =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        assertAll("",
//                () -> assertThat("",
//                        getConfigSettingsResponseBeforeDelete.data.languageTabs.size(), is(1)),
//                () -> assertThat("",
//                        updateConfigResponse.statusCode(), is(200)),
//                () -> assertThat("",
//                        getConfigSettingsResponseAfterDelete.data.languageTabs.size(), is(0))
//        );
//    }

    @Test
    @DisplayName("")
    void testAddEventToLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        GetConfigSettingsResponse getConfigSettingsResponseBeforeAdd =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateConfigRequest updateRequestBody = setRequestBodyToAddEvent();
        Response updateConfigResponse = BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);

        GetConfigSettingsResponse getConfigSettingsResponseAfterAdd =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("",
                () -> assertThat("",
                        getConfigSettingsResponseBeforeAdd.data.languageTabs.getFirst().topEvents.size(), is(0)),
                () -> assertThat("",
                        updateConfigResponse.statusCode(), is(200)),
                () -> assertThat("",
                        getConfigSettingsResponseAfterAdd.data.languageTabs.getFirst().topEvents.size(), is(1))
        );
    }

//    @Test
//    @DisplayName("")
//    void testDeleteAllSports() {
//        UpdateConfigRequest updateRequestBody = setEmptyRequestBody();
//        Response updateConfigResponse =
//                BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);
//
//        assertThat("",
//                updateConfigResponse.statusCode(), is(400));
//    }

//    @Test
//    @DisplayName("")
//    void testSetEventStatusIsSafe() {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("configId", "126");
//
//        GetConfigSettingsResponse getConfigSettingsResponseBeforeIsSafe =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        UpdateConfigRequest updateRequestBody = setRequestBodyToSetIsSafe();
//        Response updateConfigResponse =
//                BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);
//
//        GetConfigSettingsResponse getConfigSettingsResponseAfterIsSafe =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        assertAll("",
//                () -> assertThat("",
//                        getConfigSettingsResponseBeforeIsSafe.data.events.getFirst().isSafe, is(false)),
//                () -> assertThat("",
//                        updateConfigResponse.statusCode(), is(200)),
//                () -> assertThat("",
//                        getConfigSettingsResponseAfterIsSafe.data.events.getFirst().isSafe, is(true))
//        );
//    }

//    @Test
//    @DisplayName("")
//    void testSetEventStatusIsPromo() {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("configId", "126");
//
//        GetConfigSettingsResponse getConfigSettingsResponseBeforeIsPromo =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        UpdateConfigRequest updateRequestBody = setRequestBodyToSetIsPromo();
//        Response updateConfigResponse =
//                BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);
//
//        GetConfigSettingsResponse getConfigSettingsResponseAfterIsPromo =
//                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);
//
//        assertAll("",
//                () -> assertThat("",
//                        getConfigSettingsResponseBeforeIsPromo.data.events.getFirst().isPromo, is(false)),
//                () -> assertThat("",
//                        updateConfigResponse.statusCode(), is(200)),
//                () -> assertThat("",
//                        getConfigSettingsResponseAfterIsPromo.data.events.getFirst().isPromo, is(true))
//        );
//    }

//    @Test
//    @DisplayName("")
//    void testSetEventStatusIsPromoWhenIsSafe() {
//        UpdateConfigRequest updateRequestBody = setDoubleTrueStatusRequestBody();
//        Response updateConfigResponse =
//                BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);
//
//        assertThat("",
//                updateConfigResponse.statusCode(), is(400));
//    }

//    @Test
//    @DisplayName("")
//    void testGetTopSportsUpcoming() {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("timezoneOffset", "-180");
//        queryParams.put("langId", "66");
//        queryParams.put("skinName", "betsonic");
//        queryParams.put("configId", "1");
//        queryParams.put("culture", "no-no");
//        queryParams.put("countryCode", "RU");
//        queryParams.put("deviceType", "Desktop");
//        queryParams.put("numformat", "en");
//        queryParams.put("integration", "skintest");
//        queryParams.put("topSportType", "upcoming");
//
//        GetTopSportsResponse getTopSportsResponse =
//                FrontEndClient.getTopSports(baseFrontendURI, queryParams);
//
//        assertAll("",
//                () -> assertThat("",
//                        getTopSportsResponse.result.size(), is(2)),
//                () -> assertThat("",
//                        getTopSportsResponse.result.getFirst().sportId, is(106)),
//                () -> assertThat("",
//                        getTopSportsResponse.result.getLast().sportId, is(-1))
//        );
//    }


//    @Test
//    @DisplayName("")
//    void testSearchEvents() {
//        SearchEventsRequest searchRequestBody = setSearchRequestBody();
//        Response searchEvents =
//                BackOfficeClient.searchEvents(baseAdminURI, searchRequestBody, authCookies);
//
//        assertThat("",
//                searchEvents.statusCode(), is(400));
//    }
}
