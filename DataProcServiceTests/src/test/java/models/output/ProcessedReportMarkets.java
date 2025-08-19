package models.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ProcessedReportMarkets {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("is_success")
    private final boolean isSuccess;

    @JsonProperty("processed_markets_ids")
    private final List<Long> processedMarketsIds;

    @JsonProperty("processed_selections_ids")
    private final List<Long> processedSelectionsIds;

    public ProcessedReportMarkets(Long id, List<Long> processedMarketsIds, List<Long> processedSelectionsIds) {
        this.id = id;
        this.isSuccess = true;
        this.processedMarketsIds = processedMarketsIds;
        this.processedSelectionsIds = processedSelectionsIds;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess;
    }
}
