package models;

public class MarketDataRecord {
    private String eventId;
    private long marketTypeId;
    private long selectionTypeId;
    private double price;
    private double probability;
    private String status;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eveintId) {
        this.eventId = eveintId;
    }

    public long getMarketTypeId() {
        return marketTypeId;
    }

    public void setMarketTypeId(long marketTypeId) {
        this.marketTypeId = marketTypeId;
    }

    public long getSelectionTypeId() {
        return selectionTypeId;
    }

    public void setSelectionTypeId(long selectionTypeId) {
        this.selectionTypeId = selectionTypeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
