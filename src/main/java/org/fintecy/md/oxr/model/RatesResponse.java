package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.fintecy.md.oxr.model.Currency.currency;

public class RatesResponse extends HashMap<Currency, ExchangeRate> {
    private final Instant timestamp;
    private final Currency base;

    @JsonCreator
    public RatesResponse(@JsonProperty("timestamp") Instant timestamp,
                         @JsonProperty("base") String base,
                         @JsonProperty("rates") Map<Currency, ExchangeRate> rates,
                         @JsonProperty(value = "disclaimer") String disclaimer,
                         @JsonProperty(value = "license") String license) {
        this.timestamp = timestamp;
        this.base = currency(base);
        putAll(rates);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Currency getBase() {
        return base;
    }

    public Map<Currency, ExchangeRate> getRates() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RatesResponse that = (RatesResponse) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(base, that.base);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timestamp, base);
    }

    @Override
    public String toString() {
        return "RatesResponse{" +
                "timestamp=" + timestamp +
                ", base=" + base +
                "} " + super.toString();
    }
}
