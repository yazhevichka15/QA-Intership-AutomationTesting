package HighlightsManager;

import ConfigReader.ConfigReader;
import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import models.updateConfig.LanguageTab;
import models.updateConfig.Sport;
import models.updateConfig.UpdateConfigRequest;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class HighlightManagerSteps {
    private final static ConfigReader configReader = new ConfigReader();

    @Step("")
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

    @Step("")
    public static UpdateConfigRequest setRequestBodyToAddLanguage() {
        UpdateConfigRequest requestBody = new UpdateConfigRequest();
        requestBody.configId = 126;

        requestBody.highlightsEvents = new ArrayList<>();
        requestBody.languageTabs = new ArrayList<>();
        requestBody.sports = new ArrayList<>();

        LanguageTab languageTab0 = new LanguageTab();
        languageTab0.highlightsEvents = new ArrayList<>();
        languageTab0.languageId = 111;
        requestBody.languageTabs.add(languageTab0);

        Sport sport0 = new Sport();
        sport0.sportId = 99;
        sport0.order = 1;
        sport0.name = "Archery";
        sport0.isEnabled = true;
        sport0.categories = new ArrayList<>();
        sport0.count = 0;
        requestBody.sports.add(sport0);

        return requestBody;
    }
}
