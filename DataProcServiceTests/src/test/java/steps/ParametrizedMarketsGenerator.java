package steps;

import lombok.Getter;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class ParametrizedMarketsGenerator extends MarketsGenerator {

    private final MarketEvent parametrizedMarketEvent;
    private final MarketReport parametrizedMarketReport;

    private final List<MarketDataRecord> parametrizedMarketDataRecords;
    private final List<MarketDataRecord> parametrizedMarketReportDataRecords;

    private final ProcessedMarkets parametrizedProcessedMarkets;
    private final ProcessedReportMarkets parametrizedProcessedReportMarkets;

    public ParametrizedMarketsGenerator(String id, SelectionsStatuses status) {
        super(id);

        this.parametrizedMarketEvent = createMarketEventWithStatus(Long.parseLong(id), 3, status);
        this.parametrizedMarketReport = createMarketReportWithStatus(Long.parseLong(id), 3, status);

        this.parametrizedMarketDataRecords = convertParametrizedMarketEventToMarketData();
        this.parametrizedMarketReportDataRecords = convertParametrizedMarketReportToMarketData();

        this.parametrizedProcessedMarkets = getParametrizedProcessedMarkets();
        this.parametrizedProcessedReportMarkets = getParametrizedProcessedReportMarkets();
    }

    private MarketEvent createMarketEventWithStatus(long id, int marketsCount, SelectionsStatuses status) {
        return new MarketEvent(
                id,
                status.toStringStatus(),
                createMarkets(marketsCount, status, this::createEventMarket)
        );
    }

    private MarketReport createMarketReportWithStatus(long id, int marketsCount, SelectionsStatuses status) {
        return new MarketReport(
                id,
                createMarkets(marketsCount, status, this::createReportMarket)
        );
    }

    protected <T> List<T> createMarkets(long count, SelectionsStatuses status, Function<SelectionsStatuses, T> marketCreator) {
        List<T> markets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            markets.add(marketCreator.apply(status));
        }
        return markets;
    }

    private EventMarket createEventMarket(SelectionsStatuses status) {
        Map<String, Object> randomMarket = MarketCatalog.getRandomMarket();
        Long marketTypeId = (Long) randomMarket.get("market_type_id");

        return new EventMarket(
                marketTypeId,
                List.of(createSpecifier(), createSpecifier()),
                createSelections(randomMarket, status, id -> createEventSelection(id, status))
        );
    }

    private ReportMarket createReportMarket(SelectionsStatuses status) {
        Map<String, Object> randomMarket = MarketCatalog.getRandomMarket();
        Long marketTypeId = (Long) randomMarket.get("market_type_id");

        return new ReportMarket(
                marketTypeId,
                createSelections(randomMarket, status, id -> createReportSelection(id, status))
        );
    }

    protected <T> List<T> createSelections(Map<String, Object> market, SelectionsStatuses status, Function<Long, T> selectionCreator) {
        List<T> selections = new ArrayList<>();
        List<Long> selectionIds = (List<Long>) market.get("selections_ids");
        for (Long selectionId : selectionIds) {
            selections.add(selectionCreator.apply(selectionId));
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
                processedSelectionIds
        );
    }

    public String getParametrizedMarketEventAsJson() {
        return toJson(parametrizedMarketEvent);
    }

    public String getParametrizedMarketReportAsJson() {
        return toJson(parametrizedMarketReport);
    }

    public String getParametrizedProcessedMarketsAsJson() {
        return toJson(parametrizedProcessedMarkets);
    }

    public String getParametrizedProcessedReportMarketsAsJson() {
        return toJson(parametrizedProcessedReportMarkets);
    }
}


