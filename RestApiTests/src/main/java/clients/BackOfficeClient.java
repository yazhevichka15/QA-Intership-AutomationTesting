package clients;

import io.restassured.response.Response;
import models.GetConfigSettings.*;
import models.SearchEvents.SearchEventsRequest;
import models.UpdateConfig.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import io.restassured.http.Cookies;

public class BackOfficeClient {
    public static GetConfigSettingsResponse getConfigSettings(String baseURI, Map<String, String> queryParam, Cookies cookie) {
        return given()
                .baseUri(baseURI)
                .queryParams(queryParam)
                .cookies(cookie)
                .when()
                .get("/Api/HighlightsManager/GetConfigSettings")
                .then()
                .statusCode(200)
                .extract()
                .response().as(GetConfigSettingsResponse.class);
    }

    public static Response updateConfig(String baseURI, UpdateConfigRequest requestBody, Cookies cookie) {
        return given()
                .baseUri(baseURI)
                .contentType("application/json")
                .cookies(cookie)
                .body(requestBody)
                .when()
                .post("/Api/HighlightsManager/UpdateConfig")
                .then()
                .extract()
                .response();
    }

    public static Response searchEvents(String baseURI, SearchEventsRequest requestBody, Cookies cookie) {
        return given()
                .baseUri(baseURI)
                .contentType("application/json")
                .cookies(cookie)
                .body(requestBody)
                .when()
                .post("/Api/HighlightsManager/SearchEvents")
                .then()
                .extract()
                .response();
    }
}
