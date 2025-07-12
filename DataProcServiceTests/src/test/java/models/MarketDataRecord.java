package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketDataRecord {
    private String eventId;
    private Long marketTypeId;
    private Long selectionTypeId;
    private Double price;
    private Double probability;
    private String status;

    public MarketDataRecord() {}

    public MarketDataRecord(String eventId, Long marketTypeId, Long selectionTypeId,
                            Double price, Double probability, String status) {
        this.eventId = eventId;
        this.marketTypeId = marketTypeId;
        this.selectionTypeId = selectionTypeId;
        this.price = price;
        this.probability = probability;
        this.status = status;
    }
}
