package clients;

import java.util.Map;

import com.altenar.sb2.admin.model.HighlightsConfigSettingsApiResult;
import com.altenar.sb2.admin.model.ApiResult;
import com.altenar.sb2.admin.model.UpdateHighlightsConfigRequest;
import com.altenar.sb2.admin.model.EventCandidateItemListApiResult;
import com.altenar.sb2.admin.model.SearchHighlightsEventsRequest;

import ConfigReader.ConfigReader;

import static io.restassured.RestAssured.given;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.http.Cookies;

public class BackOfficeClient {
    private static final String baseAdminURI = "https://sb2admin-altenar2-stage.biahosted.com";
    private static final ConfigReader configReader = new ConfigReader();

    public static Cookies getCookies() {
        return given()
                .filter(new SwaggerCoverageRestAssured())
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

    public static HighlightsConfigSettingsApiResult getConfigSettings(Map<String, String> queryParam, Cookies cookie) {
        return given()
                .filter(new SwaggerCoverageRestAssured())
                .baseUri(baseAdminURI)
                .queryParams(queryParam)
                .cookies(cookie)
                .when()
                .get("/Api/HighlightsManager/GetConfigSettings")
                .then()
                .statusCode(200)
                .extract()
                .response().as(HighlightsConfigSettingsApiResult.class);
    }

    public static ApiResult updateConfig(UpdateHighlightsConfigRequest requestBody, Cookies cookie) {
        return given()
                .filter(new SwaggerCoverageRestAssured())
                .baseUri(baseAdminURI)
                .contentType("application/json")
                .cookies(cookie)
                .body(requestBody)
                .when()
                .post("/Api/HighlightsManager/UpdateConfig")
                .then()
                .extract()
                .response().as(ApiResult.class);
    }

    public static EventCandidateItemListApiResult searchEvents(SearchHighlightsEventsRequest requestBody, Cookies cookie) {
        return given()
                .filter(new SwaggerCoverageRestAssured())
                .baseUri(baseAdminURI)
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
