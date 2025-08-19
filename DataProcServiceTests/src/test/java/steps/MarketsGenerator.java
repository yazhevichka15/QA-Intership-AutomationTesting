package steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import models.MarketDataRecord;
import models.input.SelectionsStatuses;
import models.input.event.*;
import models.input.report.*;
import models.output.ErrorMessage;
import models.output.ProcessedMarkets;
import models.output.ProcessedReportMarkets;
import utils.*;

@Getter
public class MarketsGenerator {
    protected static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final MarketEvent marketEvent;
    private final MarketReport marketReport;

    private final List<MarketDataRecord> marketDataRecords;
    private final List<MarketDataRecord> marketDataReportRecords;

    private final ProcessedMarkets processedMarketsMessage;
    private final ProcessedReportMarkets processedReportMarketsMessage;
    private final ErrorMessage errorMessage;

    public MarketsGenerator(String id) {
        this.marketEvent = createMarketEvent(Long.parseLong(id), 3);
        this.marketReport = createMarketReport(Long.parseLong(id), 3);

        this.marketDataRecords = convertMarketEventToMarketData();
        this.marketDataReportRecords = convertMarketReportToMarketData();

        this.processedMarketsMessage = getProcessedMarketsMessage();
        this.processedReportMarketsMessage = getProcessedReportMarketsMessage();
        this.errorMessage = getErrorMessage();
    }

    private MarketEvent createMarketEvent(long id, int marketsCount) {
        SelectionsStatuses randomStatus = getRandomStatus();
        return new MarketEvent(
                id,
                randomStatus.toStringStatus(),
                createMarkets(marketsCount, MarketsGenerator::createEventMarket)
        );
    }

    private MarketReport createMarketReport(long id, int marketsCount) {
        return new MarketReport(
                id,
                createMarkets(marketsCount, MarketsGenerator::createReportMarket)
        );
    }

    protected <T> List<T> createMarkets(long count, Supplier<T> marketCreator) {
        List<T> markets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            markets.add(marketCreator.get());
        }
        return markets;
    }

    private static EventMarket createEventMarket() {
        Map<String, Object> randomMarket = MarketCatalog.getRandomMarket();
        Long marketTypeId = (Long)randomMarket.get("market_type_id");

        return new EventMarket(
                marketTypeId,
                List.of(createSpecifier(), createSpecifier()),
                createSelections(randomMarket, MarketsGenerator::createEventSelection)
        );
    }

    private static ReportMarket createReportMarket() {
        Map<String, Object> randomMarket = MarketCatalog.getRandomMarket();
        Long marketTypeId = (Long)randomMarket.get("market_type_id");

        return new ReportMarket(
                marketTypeId,
                createSelections(randomMarket, MarketsGenerator::createReportSelection)
        );
    }

    protected static Specifier createSpecifier() {
        return new Specifier(
                RandomUtils.getRandomString(10),
                RandomUtils.getRandomDouble(0.1, 10.0));
    }

    protected static <T> List<T> createSelections(Map<String, Object> market, Function<Long, T> selectionCreator) {
        List<T> selections = new ArrayList<>();
        List<Long> selectionIds = (List<Long>) market.get("selections_ids");
        for (Long selectionId : selectionIds) {
            selections.add(selectionCreator.apply(selectionId));
        }
        return selections;
    }

    private static EventSelection createEventSelection(long selectionId) {
        SelectionsStatuses randomStatus = getRandomStatus();
        return new EventSelection(
                selectionId,
                randomStatus.getCode(),
                createOdds()
        );
    }

    private static ReportSelection createReportSelection(long selectionId) {
        SelectionsStatuses randomStatus = getRandomStatus();
        return new ReportSelection(
                selectionId,
                randomStatus.toStringStatus()
        );
    }

    protected static Odds createOdds() {
        return new Odds(
                RandomUtils.getRandomDouble(1.5, 10.5),
                RandomUtils.getRandomDouble(0.01, 0.99)
        );
    }

    private List<MarketDataRecord> convertMarketEventToMarketData() {
        Long eventId = this.marketEvent.getId();
        return this.marketEvent.getMarkets()
                .stream()
                .flatMap(market -> market.getSelections()
                        .stream()
                        .map(selection -> {
                            Integer statusInt = selection.getStatus();
                            SelectionsStatuses status = SelectionsStatuses.of(statusInt);
                            Odds odds = selection.getOdds();

                            Double price = status.isFinal() ? null : odds.getPrice();
                            Double probability = status.isFinal() ? null : odds.getProbability();

                            return new MarketDataRecord(
                                    eventId.toString(),
                                    market.getMarketTypeId(),
                                    selection.getSelectionTypeId(),
                                    price,
                                    probability,
                                    status.toStringStatus()
                            );
                        }))
                .toList();
    }

    private List<MarketDataRecord> convertMarketReportToMarketData() {
        Long eventId = this.marketReport.getId();
        return this.marketReport.getMarkets()
                .stream()
                .flatMap(market -> market.getSelections()
                        .stream()
                        .map(selection -> {
                            String statusString = selection.getStatus();
                            SelectionsStatuses status = SelectionsStatuses.fromString(statusString);
                            Long selectionTypeId = selection.getSelectionTypeId();

                            Double price = (selectionTypeId % 2 == 0 ? 1.5 + selectionTypeId : 2.5 + selectionTypeId);
                            Double probability = (selectionTypeId % 2 == 0 ? 0.445 + (selectionTypeId / 10.0) : 0.555 + (selectionTypeId / 10.0));

                            return new MarketDataRecord(
                                    eventId.toString(),
                                    market.getMarketTypeId(),
                                    selectionTypeId,
                                    price,
                                    probability,
                                    status.toStringStatus()
                            );
                        }))
                .toList();
    }

    private ProcessedMarkets getProcessedMarketsMessage() {
        Long eventId = this.marketEvent.getId();
        Set<Long> uniqueMarketsIds = this.marketEvent.getMarkets()
                .stream()
                .map(EventMarket::getMarketTypeId)
                .collect(Collectors.toSet());
        Set<Long> uniqueSelectionIds = this.marketEvent.getMarkets()
                .stream()
                .flatMap(market -> market.getSelections().stream())
                .map(EventSelection::getSelectionTypeId)
                .collect(Collectors.toSet());

        return new ProcessedMarkets(
                eventId,
                uniqueMarketsIds,
                uniqueSelectionIds
        );
    }

    private ProcessedReportMarkets getProcessedReportMarketsMessage() {
        Long reportId = this.marketReport.getId();
        List<Long> processedMarketsIds = this.marketReport.getMarkets()
                .stream()
                .map(ReportMarket::getMarketTypeId)
                .toList();
        List<Long> processedSelectionsIds = this.marketReport.getMarkets()
                .stream()
                .flatMap(market -> market.getSelections().stream())
                .map(ReportSelection::getSelectionTypeId)
                .toList();

        return new ProcessedReportMarkets(
                reportId,
                processedMarketsIds,
                processedSelectionsIds
        );
    }

    private ErrorMessage getErrorMessage() {
        return new ErrorMessage();
    }

    public String getInvalidJson() {
        return """
                { }
                """;
    }

    protected String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    public String getMarketEventAsJson() {
        return toJson(marketEvent);
    }

    public String getMarketReportAsJson() {
        return toJson(marketReport);
    }

    public String getProcessedMarketsAsJson() {
        return toJson(processedMarketsMessage);
    }

    public String getProcessedReportMarketsAsJson() {
        return toJson(processedReportMarketsMessage);
    }

    public String getErrorMessageAsJson() {
        return toJson(errorMessage);
    }

    protected static SelectionsStatuses getRandomStatus() {
        return RandomUtils.getRandomItemFromList(List.of(SelectionsStatuses.values()));
    }
}