package models.getTopSports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseResult {
    @JsonProperty("SportId")
    public int sportId;
    @JsonProperty("SortOrder")
    public long sortOrder;
    @JsonProperty("SportTypeId")
    public int sportTypeId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("ISO")
    public String iSO;
    @JsonProperty("HasLiveEvents")
    public boolean hasLiveEvents;
    @JsonProperty("EventCount")
    public int eventCount;
}
