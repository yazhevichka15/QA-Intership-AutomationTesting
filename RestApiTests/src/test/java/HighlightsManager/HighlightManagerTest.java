package HighlightsManager;

import ConfigReader.*;
import clients.*;
import models.*;
import models.responses.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HighlightManagerTest {
    private final String baseAdminURI = "https://sb2admin-altenar2-stage.biahosted.com";
    private final String baseFrontendURI = "https://sb2frontend-altenar2-stage.biahosted.com";

    private final ConfigReader configReader = new ConfigReader();
    private Cookies authCookies;

    @BeforeEach
    @Step("")
    void setUp() {
        authCookies = getCookies();
    }

    @Test
    @DisplayName("")
    void testAddLanguage() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("configId", "126");

        GetConfigSettingsResponse getConfigSettingsResponse =
                BackOfficeClient.getConfigSettings(baseAdminURI, queryParams, authCookies);

        assertThat("",
                getConfigSettingsResponse.success, is(true));
    }

    private Cookies getCookies() {
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
}
