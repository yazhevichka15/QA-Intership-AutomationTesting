package clients;

import io.restassured.response.Response;
import java.util.Map;

import com.altenar.sb2.admin.model.HighlightsConfigSettingsApiResult;
import com.altenar.sb2.admin.model.ApiResult;
import com.altenar.sb2.admin.model.UpdateHighlightsConfigRequest;
import com.altenar.sb2.admin.model.EventCandidateItemListApiResult;
import com.altenar.sb2.admin.model.SearchHighlightsEventsRequest;

import static io.restassured.RestAssured.given;
import io.restassured.http.Cookies;

public class BackOfficeClient {
    public static HighlightsConfigSettingsApiResult getConfigSettings(String baseURI, Map<String, String> queryParam, Cookies cookie) {
        return given()
                .baseUri(baseURI)
                .queryParams(queryParam)
                .cookies(cookie)
                .when()
                .get("/Api/HighlightsManager/GetConfigSettings")
                .then()
                .statusCode(200)
                .extract()
                .response().as(HighlightsConfigSettingsApiResult.class);
    }

    public static ApiResult updateConfig(String baseURI, UpdateHighlightsConfigRequest requestBody, Cookies cookie) {
        return given()
                .baseUri(baseURI)
                .contentType("application/json")
                .cookies(cookie)
                .body(requestBody)
                .when()
                .post("/Api/HighlightsManager/UpdateConfig")
                .then()
                .extract()
                .response().as(ApiResult.class);
    }

    public static EventCandidateItemListApiResult searchEvents(String baseURI, SearchHighlightsEventsRequest requestBody, Cookies cookie) {
        return given()
                .baseUri(baseURI)
                .contentType("application/json")
                .cookies(cookie)
                .body(requestBody)
                .when()
                .post("/Api/HighlightsManager/SearchEvents")
                .then()
                .extract()
                .response().as(EventCandidateItemListApiResult.class);
    }
}
