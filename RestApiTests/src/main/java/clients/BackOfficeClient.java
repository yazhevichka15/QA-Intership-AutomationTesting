package clients;

import models.responses.GetConfigSettingsResponse;

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
                .response()
                .as(GetConfigSettingsResponse.class);
    }
}
