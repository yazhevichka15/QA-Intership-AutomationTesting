package models.input.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ReportSelection {
    @JsonProperty("selection_type_id")
    private final Long selectionTypeId;

    @JsonProperty("status")
    private final String status;

    public ReportSelection(Long selectionTypeId, String status) {
        this.selectionTypeId = selectionTypeId;
        this.status = status;
    }
}
