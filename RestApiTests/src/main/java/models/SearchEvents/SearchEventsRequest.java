package models.SearchEvents;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SearchEventsRequest {
    public String dateFrom;
    public String dateTo;
    public ArrayList<Integer> sportIds;
    public int champId;
}
