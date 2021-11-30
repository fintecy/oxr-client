package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class TimeSeriesResponse {
    private final LocalDate start;
    private final LocalDate end;
    private final Map<LocalDate, Map<String, BigDecimal>> rates;

    @JsonCreator
    public TimeSeriesResponse(@JsonProperty("start") LocalDate start,
                              @JsonProperty("end") LocalDate end,
                              @JsonProperty("rates") Map<LocalDate, Map<String, BigDecimal>> rates,
                              @JsonProperty("disclaimer") String disclaimer,
                              @JsonProperty("license") String license) {
        this.start = start;
        this.end = end;
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "TimeSeriesResponse{" +
                "start=" + start +
                ", end=" + end +
                ", rates=" + rates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeriesResponse that = (TimeSeriesResponse) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, rates);
    }
}
