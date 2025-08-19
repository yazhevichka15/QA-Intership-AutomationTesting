package utils;

import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

public class JsonTestUtils {
    public static void assertJsonEquals(String actualJson, String expectedJson) {
        JsonFluentAssert.assertThatJson(actualJson)
                .isEqualTo(expectedJson);
    }
}