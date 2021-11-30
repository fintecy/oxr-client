package org.fintecy.md.oxr.model.usage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Usage {
    private final int requests;
    private final int requestsQuota;
    private final int requestsRemaining;
    private final int daysElapsed;
    private final int daysRemaining;
    private final int dailyAverage;

    @JsonCreator
    public Usage(@JsonProperty("requests") int requests,
                 @JsonProperty("requests_quota") int requestsQuota,
                 @JsonProperty("requests_remaining") int requestsRemaining,
                 @JsonProperty("days_elapsed") int daysElapsed,
                 @JsonProperty("days_remaining") int daysRemaining,
                 @JsonProperty("daily_average") int dailyAverage) {
        this.requests = requests;
        this.requestsQuota = requestsQuota;
        this.requestsRemaining = requestsRemaining;
        this.daysElapsed = daysElapsed;
        this.daysRemaining = daysRemaining;
        this.dailyAverage = dailyAverage;
    }

    public int getRequests() {
        return requests;
    }

    public int getRequestsQuota() {
        return requestsQuota;
    }

    public int getRequestsRemaining() {
        return requestsRemaining;
    }

    public int getDaysElapsed() {
        return daysElapsed;
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }

    public int getDailyAverage() {
        return dailyAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usage)) return false;
        Usage usage = (Usage) o;
        return requests == usage.requests && requestsQuota == usage.requestsQuota && requestsRemaining == usage.requestsRemaining && daysElapsed == usage.daysElapsed && daysRemaining == usage.daysRemaining && dailyAverage == usage.dailyAverage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requests, requestsQuota, requestsRemaining, daysElapsed, daysRemaining, dailyAverage);
    }

    @Override
    public String toString() {
        return "Usage{" +
                "requests=" + requests +
                ", requestsQuota=" + requestsQuota +
                ", requestsRemaining=" + requestsRemaining +
                ", daysElapsed=" + daysElapsed +
                ", daysRemaining=" + daysRemaining +
                ", dailyAverage=" + dailyAverage +
                '}';
    }
}
