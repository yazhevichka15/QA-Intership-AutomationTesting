package models.input.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Odds {
    @JsonProperty("price")
    private final Double price;

    @JsonProperty("probability")
    private final Double probability;

    public Odds(Double price, Double probability) {
        this.price = price;
        this.probability = probability;
    }
}