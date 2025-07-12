package steps;

import models.MarketDataRecord;
import models.input.SelectionsStatuses;
import models.input.event.EventMarket;
import models.input.event.EventSelection;
import models.input.event.MarketEvent;
import models.input.event.Odds;
import models.input.report.MarketReport;
import models.input.report.ReportMarket;
import models.input.report.ReportSelection;
import models.output.ProcessedMarkets;
import models.output.ProcessedReportMarkets;
import utils.MarketCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParametrizedMarketsGenerator extends MarketsGenerator {

    private final MarketEvent parametrizedMarketEvent;
    private final MarketReport parametrizedMarketReport;

    private final List<MarketDataRecord> parametrizedMarketDataRecords;
    private final List<MarketDataRecord> parametrizedMarketReportDataRecords;

    private final ProcessedMarkets parametrizedProcessedMarkets;
    private final ProcessedReportMarkets parametrizedProcessedReportMarkets;

    public ParametrizedMarketsGenerator(String id, SelectionsStatuses status) {
        super(id);

        this.parametrizedMarketEvent = createMarketEventWithStatus(Long.parseLong(id), 2, status);
        this.parametrizedMarketReport = createMarketReportWithStatus(Long.parseLong(id), 2, status);

        this.parametrizedMarketDataRecords = convertParametrizedMarketEventToMarketData();
        this.parametrizedMarketReportDataRecords = convertParametrizedMarketReportToMarketData();

        this.parametrizedProcessedMarkets = getParametrizedProcessedMarkets();
        this.parametrizedProcessedReportMarkets = getParametrizedProcessedReportMarkets();
    }

    private MarketEvent createMarketEventWithStatus(long id, int marketsCount, SelectionsStatuses status) {
        return new MarketEvent(
                id,
                status.toStringStatus(),
                createEventMarkets(marketsCount, status)
        );
    }

    private MarketReport createMarketReportWithStatus(long id, int marketsCount, SelectionsStatuses status) {
        return new MarketReport(
                id,
                createReportMarkets(marketsCount, status)
        );
    }

    private List<EventMarket> createEventMarkets(long count, SelectionsStatuses status) {
        List<EventMarket> markets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            EventMarket market = createEventMarket(status);
            markets.add(market);
        }
        return markets;
    }

    private List<ReportMarket> createReportMarkets(long count, SelectionsStatuses status) {
        List<ReportMarket> markets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ReportMarket market = createReportMarket(status);
            markets.add(market);
        }
        return markets;
    }

    private EventMarket createEventMarket(SelectionsStatuses status) {
        Map<String, Object> randomMarket = MarketCatalog.getRandomMarket();
        Long marketTypeId = (Long) randomMarket.get("market_type_id");

        return new EventMarket(
                marketTypeId,
                List.of(createSpecifier(), createSpecifier()),
                createEventSelections(randomMarket, status)
        );
    }

    private ReportMarket createReportMarket(SelectionsStatuses status) {
        Map<String, Object> randomMarket = MarketCatalog.getRandomMarket();
        Long marketTypeId = (Long) randomMarket.get("market_type_id");

        return new ReportMarket(
                marketTypeId,
                createReportSelections(randomMarket, status)
        );
    }

    private List<EventSelection> createEventSelections(Map<String, Object> market, SelectionsStatuses status) {
        List<EventSelection> selections = new ArrayList<>();
        List<Long> selectionIds = (List<Long>) market.get("selections_ids");

        for (Long selectionId : selectionIds) {
            EventSelection selection = createEventSelection(selectionId, status);
            selections.add(selection);
        }
        return selections;
    }

    private List<ReportSelection> createReportSelections(Map<String, Object> market, SelectionsStatuses status) {
        List<ReportSelection> selections = new ArrayList<>();
        List<Long> selectionIds = (List<Long>) market.get("selections_ids");

        for (Long selectionId : selectionIds) {
            ReportSelection selection = createReportSelection(selectionId, status);
            selections.add(selection);
        }
        return selections;
    }

    private EventSelection createEventSelection(long selectionId, SelectionsStatuses status) {
        return new EventSelection(
                selectionId,
                status.getCode(),
                createOdds()
        );
    }

    private ReportSelection createReportSelection(long selectionId, SelectionsStatuses status) {
        return new ReportSelection(
                selectionId,
                status.toStringStatus()
        );
    }


    private List<MarketDataRecord> convertParametrizedMarketEventToMarketData() {
        Long eventId = this.parametrizedMarketEvent.getId();
        return this.parametrizedMarketEvent.getMarkets()
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

    private List<MarketDataRecord> convertParametrizedMarketReportToMarketData() {
        Long eventId = this.parametrizedMarketReport.getId();
        return this.parametrizedMarketReport.getMarkets()
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

    private ProcessedMarkets getParametrizedProcessedMarkets() {
        Long eventId = this.parametrizedMarketEvent.getId();
        Set<Long> uniqueMarketsIds = this.parametrizedMarketDataRecords
                .stream()
                .map(MarketDataRecord::getMarketTypeId)
                .collect(Collectors.toSet());
        Set<Long> uniqueSelectionIds = this.parametrizedMarketDataRecords
                .stream()
                .map(MarketDataRecord::getSelectionTypeId)
                .collect(Collectors.toSet());

        return new ProcessedMarkets(
                eventId,
                uniqueMarketsIds,
                uniqueSelectionIds
        );
    }

    private ProcessedReportMarkets getParametrizedProcessedReportMarkets() {
        Long reportId = this.parametrizedMarketReport.getId();
        List<Long> processedMarketsIds = this.parametrizedMarketReport.getMarkets()
                .stream()
                .map(ReportMarket::getMarketTypeId)
                .toList();
        List<Long> processedSelectionIds = this.parametrizedMarketReport.getMarkets()
                .stream()
                .flatMap(market -> market.getSelections().stream())
                .map(ReportSelection::getSelectionTypeId)
                .toList();

        return new ProcessedReportMarkets(
                reportId,
                processedMarketsIds,
                processedSelectionIds);
    }

    public String getParametrizedMarketEventAsJson() {
        try {
            return MAPPER.writeValueAsString(parametrizedMarketEvent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize parametrized MarketEvent to JSON", e);
        }
    }

    public String getParametrizedProcessedMarketsAsJson() {
        try {
            return MAPPER.writeValueAsString(parametrizedProcessedMarkets);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize parametrized ProcessedMarkets to JSON", e);
        }
    }

    public List<MarketDataRecord> getParametrizedMarketDataRecords() {
        return parametrizedMarketDataRecords;
    }

    public String getParametrizedMarketReportAsJson() {
        try {
            return MAPPER.writeValueAsString(parametrizedMarketReport);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize parametrized MarketReport to JSON", e);
        }
    }

    public List<MarketDataRecord> getParametrizedMarketReportDataRecords() {
        return parametrizedMarketReportDataRecords;
    }

    public String getParametrizedProcessedReportMarketsAsJson() {
        try {
            return MAPPER.writeValueAsString(parametrizedProcessedReportMarkets);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize parametrized ProcessedReportMarkets to JSON", e);
        }
    }
}


