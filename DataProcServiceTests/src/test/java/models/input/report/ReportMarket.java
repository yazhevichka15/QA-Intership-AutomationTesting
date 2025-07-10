package models.input.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ReportMarket {
    @JsonProperty("market_type_id")
    private final Long marketTypeId;

    @JsonProperty("selections")
    private final List<ReportSelection> selections;

    public ReportMarket(Long marketTypeId, List<ReportSelection> selections) {
        this.marketTypeId = marketTypeId;
        this.selections = selections;
    }
}
