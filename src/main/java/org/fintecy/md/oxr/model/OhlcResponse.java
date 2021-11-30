package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;

public class OhlcResponse {
    private final Instant start;
    private final Instant end;
    private final Currency base;
    private final Map<String, CandleStick> rates;

    @JsonCreator
    public OhlcResponse(@JsonProperty("start_time") Instant start,
                        @JsonProperty("end_time") Instant end,
                        @JsonProperty("base") Currency base,
                        @JsonProperty("rates") Map<String, CandleStick> rates,
                        @JsonProperty("disclaimer") String disclaimer,
                        @JsonProperty("license") String license) {
        this.start = start;
        this.end = end;
        this.base = base;
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "OhlcResponse{" +
                "start=" + start +
                ", end=" + end +
                ", base=" + base +
                ", rates=" + rates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OhlcResponse that = (OhlcResponse) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(base, that.base) && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, base, rates);
    }
}
