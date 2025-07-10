package models.input.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EventSelection {
    @JsonProperty("selection_type_id")
    private final Long selectionTypeId;

    @JsonProperty("status")
    private final Integer status;

    @JsonProperty("odds")
    private final Odds odds;

    public EventSelection(Long selectionTypeId, Integer status, Odds odds) {
        this.selectionTypeId = selectionTypeId;
        this.status = status;
        this.odds = odds;
    }
}