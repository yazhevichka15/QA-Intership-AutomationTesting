package models.input.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Specifier {
    @JsonProperty("name")
    private final String name;

    @JsonProperty("value")
    private final Double value;

    public Specifier(String name, Double value) {
        this.name = name;
        this.value = value;
    }
}