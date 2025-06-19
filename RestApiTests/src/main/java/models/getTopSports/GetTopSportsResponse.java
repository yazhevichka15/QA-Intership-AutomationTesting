package models.getTopSports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetTopSportsResponse {
    @JsonProperty("Result")
    public ArrayList<ResponseResult> result;
}
