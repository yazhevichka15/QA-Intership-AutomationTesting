package utils;

import java.util.List;
import java.util.Map;

public class MarketCatalog {
    private static final List<Map<String, Object>> CATALOG = List.of(
            Map.of("market_type_id", 2001L, "selections_ids", List.of(1L, 2L)),
            Map.of("market_type_id", 2002L, "selections_ids", List.of(3L, 4L, 5L)),
            Map.of("market_type_id", 2003L, "selections_ids", List.of(6L, 7L)),
            Map.of("market_type_id", 2004L, "selections_ids", List.of(8L, 9L)),
            Map.of("market_type_id", 2005L, "selections_ids", List.of(10L, 11L)),
            Map.of("market_type_id", 2006L, "selections_ids", List.of(12L, 13L, 14L)),
            Map.of("market_type_id", 2007L, "selections_ids", List.of(15L, 16L)),
            Map.of("market_type_id", 2008L, "selections_ids", List.of(17L, 18L)),
            Map.of("market_type_id", 2009L, "selections_ids", List.of(19L, 20L, 21L, 22L, 23L, 24L)),
            Map.of("market_type_id", 2010L, "selections_ids", List.of(25L, 26L, 27L)),
            Map.of("market_type_id", 2011L, "selections_ids", List.of(28L, 29L)),
            Map.of("market_type_id", 2012L, "selections_ids", List.of(30L, 31L, 32L, 33L, 34L)),
            Map.of("market_type_id", 2013L, "selections_ids", List.of(35L, 36L, 37L, 38L)),
            Map.of("market_type_id", 2014L, "selections_ids", List.of(39L, 40L, 41L)),
            Map.of("market_type_id", 2015L, "selections_ids", List.of(42L, 43L, 44L, 45L)),
            Map.of("market_type_id", 2016L, "selections_ids", List.of(46L, 47L, 48L, 49L, 50L, 51L)),
            Map.of("market_type_id", 2017L, "selections_ids", List.of(52L, 53L, 54L, 55L, 56L, 57L, 58L, 59L, 60L, 61L)),
            Map.of("market_type_id", 2018L, "selections_ids", List.of(62L, 63L, 64L, 65L, 66L, 67L, 68L, 69L, 70L, 71L, 72L, 73L)),
            Map.of("market_type_id", 2019L, "selections_ids", List.of(74L, 75L, 76L)),
            Map.of("market_type_id", 2020L, "selections_ids", List.of(77L, 78L, 79L, 80L, 81L, 82L, 83L, 84L, 85L, 86L, 87L, 88L)),
            Map.of("market_type_id", 2021L, "selections_ids", List.of(89L, 90L, 91L, 92L, 93L, 94L, 95L, 96L, 97L, 98L, 99L, 100L, 101L, 102L)),
            Map.of("market_type_id", 2022L, "selections_ids", List.of(103L, 104L, 105L, 106L, 107L, 108L, 109L)),
            Map.of("market_type_id", 2023L, "selections_ids", List.of(110L, 111L, 112L)),
            Map.of("market_type_id", 2024L, "selections_ids", List.of(113L, 114L)),
            Map.of("market_type_id", 2025L, "selections_ids", List.of(115L, 116L, 117L)),
            Map.of("market_type_id", 2026L, "selections_ids", List.of(118L, 119L)),
            Map.of("market_type_id", 2027L, "selections_ids", List.of(120L, 121L, 122L, 123L)),
            Map.of("market_type_id", 2028L, "selections_ids", List.of(124L, 125L)),
            Map.of("market_type_id", 2037L, "selections_ids", List.of(144L, 145L)),
            Map.of("market_type_id", 2038L, "selections_ids", List.of(146L, 147L))
    );

    public static Map<String, Object> getRandomMarket() {
        return RandomUtils.getRandomItemFromList(CATALOG);
    }
}
