package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Currency;
import java.util.Objects;

public class ConvertRequest {
    private final String query;
    private final double amount;
    private final Currency from;
    private final Currency to;

    @JsonCreator
    public ConvertRequest(@JsonProperty("query") String query,
                          @JsonProperty("amount") double amount,
                          @JsonProperty("from") Currency from,
                          @JsonProperty("to") Currency to) {
        this.query = query;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "ConvertRequest{" +
                "query=" + query +
                ", amount=" + amount +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertRequest that = (ConvertRequest) o;
        return amount == that.amount
                && Objects.equals(query, that.query)
                && Objects.equals(from, that.from)
                && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, amount, from, to);
    }
}
