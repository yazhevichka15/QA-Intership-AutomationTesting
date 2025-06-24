package clients;

import models.GetTopSports.GetTopSportsResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class FrontEndClient {
    public static GetTopSportsResponse getTopSports(String baseURI, Map<String, String> queryParam) {
        return given()
                .baseUri(baseURI)
                .queryParams(queryParam)
                .when()
                .get("/api/Sportsbook/GetTopSports")
                .then()
                .statusCode(200)
                .extract()
                .response().as(GetTopSportsResponse.class);
    }
}
