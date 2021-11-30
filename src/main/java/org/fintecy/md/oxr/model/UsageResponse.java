package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fintecy.md.oxr.model.usage.UsageData;

import java.util.Objects;

public class UsageResponse {
    private final int status;
    private final UsageData data;

    @JsonCreator
    public UsageResponse(@JsonProperty("status") int status,
                         @JsonProperty("data") UsageData data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public UsageData getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsageResponse)) return false;
        var that = (UsageResponse) o;
        return status == that.status && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data);
    }

    @Override
    public String toString() {
        return "UsageResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
