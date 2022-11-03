package org.fintecy.md.oxr.model;

import org.fintecy.md.common.model.Currency;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.fintecy.md.common.model.Currency.currency;

public class RatesResponse extends HashMap<Currency, ExchangeRate> {
    private final Instant timestamp;
    private final Currency base;

    public RatesResponse(Instant timestamp,
                         String base,
                         Map<Currency, ExchangeRate> rates) {
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
