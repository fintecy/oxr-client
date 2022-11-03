package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fintecy.md.common.model.Currency;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class TimeSeriesResponse extends TreeMap<LocalDate, SortedMap<Currency, ExchangeRate>> {
    private final Currency base;

    public TimeSeriesResponse(@JsonProperty("base") Currency base,
                              @JsonProperty("rates") SortedMap<LocalDate, SortedMap<Currency, ExchangeRate>> rates) {
        super(rates);
        this.base = base;
    }

    public Currency getBase() {
        return base;
    }
}
