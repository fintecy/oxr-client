package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;

public class RatesResponse {
    private final Instant timestamp;
    private final Currency base;
    private final Map<String, BigDecimal> rates;

    @JsonCreator
    public RatesResponse(@JsonProperty("timestamp") Instant timestamp,
                         @JsonProperty("base") String base,
                         @JsonProperty("rates") Map<String, BigDecimal> rates,
                         @JsonProperty("disclaimer") String disclaimer,
                         @JsonProperty("license") String license) {
        this.timestamp = timestamp;
        this.base = Currency.getInstance(base);
        this.rates = rates;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Currency getBase() {
        return base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatesResponse)) return false;
        RatesResponse that = (RatesResponse) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(base, that.base)
                && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, base, rates);
    }

    @Override
    public String toString() {
        return "HistoricalResponse{" +
                "timestamp=" + timestamp +
                ", base=" + base +
                ", rates=" + rates +
                '}';
    }
}
