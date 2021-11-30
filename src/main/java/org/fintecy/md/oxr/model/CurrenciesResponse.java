package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Currency;
import java.util.Map;
import java.util.Objects;

public class CurrenciesResponse {
    private final Map<String, String> availableCurrencies;

    @JsonCreator
    public CurrenciesResponse(Map<String, String> availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public Map<String, String> getAvailableCurrencies() {
        return availableCurrencies;
    }

    public boolean contains(String currency) {
        return contains(Currency.getInstance(currency));
    }

    public boolean contains(Currency currency) {
        return availableCurrencies.containsKey(currency.getCurrencyCode());
    }

    public String getCurrencyName(String currency) {
        return getCurrencyName(Currency.getInstance(currency));
    }

    public String getCurrencyName(Currency currency) {
        return availableCurrencies.get(currency.getCurrencyCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrenciesResponse)) return false;
        CurrenciesResponse that = (CurrenciesResponse) o;
        return Objects.equals(availableCurrencies, that.availableCurrencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableCurrencies);
    }

    @Override
    public String toString() {
        return "CurrenciesResponse{" +
                "availableCurrencies=" + availableCurrencies +
                '}';
    }
}
