package org.fintecy.md.oxr.model;

import org.fintecy.md.common.model.Currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class ConvertResponse {
    private final Instant timestamp;
    private final Currency from;
    private final Currency to;
    private final BigDecimal baseAmount;
    private final BigDecimal counterAmount;
    private final BigDecimal rate;

    public ConvertResponse(Instant timestamp, Currency from, Currency to,
                           BigDecimal baseAmount, BigDecimal counterAmount, BigDecimal rate) {
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
        this.baseAmount = baseAmount;
        this.counterAmount = counterAmount;
        this.rate = rate;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public BigDecimal getCounterAmount() {
        return counterAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ConvertResponse{" +
                "timestamp=" + timestamp +
                ", from=" + from +
                ", to=" + to +
                ", baseAmount=" + baseAmount +
                ", counterAmount=" + counterAmount +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertResponse that = (ConvertResponse) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(baseAmount, that.baseAmount) && Objects.equals(counterAmount, that.counterAmount) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, from, to, baseAmount, counterAmount, rate);
    }
}
