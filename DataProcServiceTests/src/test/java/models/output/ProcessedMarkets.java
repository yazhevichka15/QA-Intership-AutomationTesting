package models.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Set;

@Getter
public class ProcessedMarkets {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("is_success")
    private final boolean isSuccess;

    @JsonProperty("unique_markets_ids")
    private final Set<Long> uniqueMarketsIds;

    @JsonProperty("unique_selection_ids")
    private final Set<Long> uniqueSelectionIds;

    public ProcessedMarkets(Long id, Set<Long> uniqueMarketsIds, Set<Long> uniqueSelectionIds) {
        this.id = id;
        this.isSuccess = true;
        this.uniqueMarketsIds = uniqueMarketsIds;
        this.uniqueSelectionIds = uniqueSelectionIds;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess;
    }
}