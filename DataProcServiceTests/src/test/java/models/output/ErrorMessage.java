package models.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorMessage {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("is_success")
    private final boolean isSuccess;

    @JsonProperty("error_description")
    private final String errorDescription;

    public ErrorMessage() {
        this.id = -922337203685477580L;
        this.isSuccess = false;
        this.errorDescription = "Deserialization error. Received message with wrong format.";
    }

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess;
    }
}
