package utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static String getRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static Long getRandomLongNumber(long min, long max) {
        return min + (long) (RANDOM.nextDouble() * (max - min + 1));
    }

    public static Double getRandomDouble(double min, double max) {
        return min + RANDOM.nextDouble() * (max - min);
    }

    public static <T> T getRandomItemFromList(List<T> items) {
        return items.get(RANDOM.nextInt(items.size()));
    }
}