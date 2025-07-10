package models.input.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MarketReport {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("message_type")
    private final String messageType;

    @JsonProperty("markets")
    private final List<ReportMarket> markets;

    public MarketReport(Long id, List<ReportMarket> markets) {
        this.id = id;
        this.messageType = "market_report";
        this.markets = markets;
    }
}
