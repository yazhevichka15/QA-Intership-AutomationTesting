package models.input;

import lombok.Getter;

import java.util.Set;

@Getter
public enum SelectionsStatuses {
    ACTIVE(0),
    SUSPENDED(1),
    DISABLED(2),
    WIN(3),
    LOSS(4),
    RETURN(5),
    HALF_WIN(6),
    HALF_LOSS(7),
    CANCELLED(8);

    private static final Set<SelectionsStatuses> FINAL_STATUSES = Set.of(
            DISABLED,
            WIN,
            LOSS,
            RETURN,
            HALF_WIN,
            HALF_LOSS,
            CANCELLED
    );

    private final int code;

    SelectionsStatuses(int code) {
        this.code = code;
    }

    public boolean isFinal() {
        return FINAL_STATUSES.contains(this);
    }

    public String toStringStatus() {
        return name().toLowerCase();
    }

    public static SelectionsStatuses of(int code) {
        for (SelectionsStatuses status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("No SelectionsStatuses found for code: " + code);
    }

    public static SelectionsStatuses fromString(String status) {
        for (SelectionsStatuses s : values()) {
            if (s.toStringStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No SelectionsStatuses found for string: " + status);
    }
}
