package models;

import lombok.Getter;

@Getter
public class MarketDataEntity {
    private final Long eventId;
    private final Long marketTypeId;
    private final Long selectionTypeId;
    private final Double price;
    private final Double probability;
    private final String status;

    public MarketDataEntity(Long eventId, Long marketTypeId, Long selectionTypeId,
                            Double price, Double probability, String status) {
        this.eventId = eventId;
        this.marketTypeId = marketTypeId;
        this.selectionTypeId = selectionTypeId;
        this.price = price;
        this.probability = probability;
        this.status = status;
    }
}