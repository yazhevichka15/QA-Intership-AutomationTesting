package models.input.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class EventMarket {
    @JsonProperty("market_type_id")
    private final Long marketTypeId;

    @JsonProperty("specifiers")
    private final List<Specifier> specifiers;

    @JsonProperty("selections")
    private final List<EventSelection> selections;

    public EventMarket(Long marketTypeId, List<Specifier> specifiers, List<EventSelection> selections) {
        this.marketTypeId = marketTypeId;
        this.specifiers = specifiers;
        this.selections = selections;
    }
}