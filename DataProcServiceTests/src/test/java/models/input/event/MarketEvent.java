package models.input.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MarketEvent {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("message_type")
    private final String messageType;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("markets")
    private final List<EventMarket> markets;

    public MarketEvent(Long id, String status, List<EventMarket> markets) {
        this.id = id;
        this.messageType = "market_event";
        this.status = status;
        this.markets = markets;
    }
}