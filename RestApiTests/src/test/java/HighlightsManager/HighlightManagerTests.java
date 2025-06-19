package HighlightsManager;

import ConfigReader.*;
import clients.*;
import models.getConfigSettings.*;
import models.updateConfig.UpdateConfigRequest;

import static HighlightsManager.HighlightManagerSteps.getCookies;
import static HighlightsManager.HighlightManagerSteps.setRequestBodyToAddLanguage;

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

    @Test
    @DisplayName("")
    void testAddLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        GetConfigSettingsResponse getConfigSettingsResponseBeforeAdd =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        UpdateConfigRequest updateRequestBody = setRequestBodyToAddLanguage();
        BackOfficeClient.updateConfigResponse(baseAdminURI, updateRequestBody, authCookies);

        GetConfigSettingsResponse getConfigSettingsResponseAfterAdd =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertAll("",
                () -> assertThat("",
                        getConfigSettingsResponseBeforeAdd.data.languageTabs.size(), is(0)),
                () -> assertThat("",
                        getConfigSettingsResponseAfterAdd.data.languageTabs.size(), is(1))
        );
    }
}
