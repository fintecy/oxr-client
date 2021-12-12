package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class TimeSeriesResponse extends TreeMap<LocalDate, SortedMap<Currency, ExchangeRate>> {

    @JsonCreator
    public TimeSeriesResponse(@JsonProperty("start") LocalDate start,
                              @JsonProperty("end") LocalDate end,
                              @JsonProperty("rates") SortedMap<LocalDate, SortedMap<Currency, ExchangeRate>> rates,
                              @JsonProperty("disclaimer") String disclaimer,
                              @JsonProperty("license") String license) {
        super(rates);
    }
}
