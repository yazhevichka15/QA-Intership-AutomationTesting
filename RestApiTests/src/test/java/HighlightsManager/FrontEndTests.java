package HighlightsManager;

import clients.FrontEndClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.altenar.sb2.frontend.model.TopSportFullModelOutIEnumerableApiResult;

public class FrontEndTests {
    private static final String TIMEZONE_OFFSET = "-180";
    private static final String LANG_ID = "66";
    private static final String SKIN_NAME = "betsonic";
    private static final String CONFIG_ID = "1";
    private static final String NORWEGIAN_CULTURE = "no-no";
    private static final String RU_COUNTRY_CODE = "RU";
    private static final String DESKTOP_DEVICE_TYPE = "Desktop";
    private static final String NUMFORMAT = "en";
    private static final String TEST_INTEGRATION = "skintest";
    private static final String UPCOMING_TOP_SPORT_TYPE = "upcoming";

    private static final int CRICKET_SPORT_ID = 74;
    private static final int OTHERS_SPORT_ID = -1;

    @Test
    @DisplayName("Should return top sports for type upcoming with expected structure and content")
    void testGetTopSportsUpcoming() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("timezoneOffset", TIMEZONE_OFFSET);
        queryParams.put("langId", LANG_ID);
        queryParams.put("skinName", SKIN_NAME);
        queryParams.put("configId", CONFIG_ID);
        queryParams.put("culture", NORWEGIAN_CULTURE);
        queryParams.put("countryCode", RU_COUNTRY_CODE);
        queryParams.put("deviceType", DESKTOP_DEVICE_TYPE);
        queryParams.put("numformat", NUMFORMAT);
        queryParams.put("integration", TEST_INTEGRATION);
        queryParams.put("topSportType", UPCOMING_TOP_SPORT_TYPE);

        TopSportFullModelOutIEnumerableApiResult topSports =
                FrontEndClient.getTopSports(queryParams);

        assertAll("Validate top sports response for upcoming type",
                () -> assertThat("Should return 2 top sports",
                        topSports.getResult(), hasSize(2)),

                () -> assertThat("First sport should have sportId equal to cricket's sportId",
                        topSports.getResult().getFirst().getSportId(), is(CRICKET_SPORT_ID)),

                () -> assertThat("Second sport should have sportId equal to others sportId",
                        topSports.getResult().getLast().getSportId(), is(OTHERS_SPORT_ID))
        );
    }
}
