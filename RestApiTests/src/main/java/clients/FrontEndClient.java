package clients;

import com.altenar.sb2.frontend.model.TopSportFullModelOutIEnumerableApiResult;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;

import java.util.Map;
import static io.restassured.RestAssured.given;

public class FrontEndClient {
    private static final String baseFrontendURI = "https://sb2frontend-altenar2-stage.biahosted.com";

    public static TopSportFullModelOutIEnumerableApiResult getTopSports(Map<String, String> queryParam) {
        return given()
                .filter(new SwaggerCoverageRestAssured())
                .baseUri(baseFrontendURI)
                .queryParams(queryParam)
                .when()
                .get("/api/Sportsbook/GetTopSports")
                .then()
                .statusCode(200)
                .extract()
                .response().as(TopSportFullModelOutIEnumerableApiResult.class);
    }
}
