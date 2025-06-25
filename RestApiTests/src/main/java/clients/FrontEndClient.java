package clients;

import com.altenar.sb2.frontend.model.TopSportFullModelOutIEnumerableApiResult;

import java.util.Map;
import static io.restassured.RestAssured.given;

public class FrontEndClient {
    public static TopSportFullModelOutIEnumerableApiResult getTopSports(String baseURI, Map<String, String> queryParam) {
        return given()
                .baseUri(baseURI)
                .queryParams(queryParam)
                .when()
                .get("/api/Sportsbook/GetTopSports")
                .then()
                .statusCode(200)
                .extract()
                .response().as(TopSportFullModelOutIEnumerableApiResult.class);
    }
}
