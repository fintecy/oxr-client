package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Objects;

import static org.fintecy.md.oxr.model.Currency.currency;

public class CurrenciesResponse {
    private final Map<Currency, String> availableCurrencies;

    @JsonCreator
    public CurrenciesResponse(Map<Currency, String> availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public Map<Currency, String> getAvailableCurrencies() {
        return availableCurrencies;
    }

    public boolean contains(String currency) {
        return contains(currency(currency));
    }

    public boolean contains(Currency currency) {
        return availableCurrencies.containsKey(currency);
    }

    public String getCurrencyName(String currency) {
        return getCurrencyName(currency(currency));
    }

    public String getCurrencyName(Currency currency) {
        return availableCurrencies.get(currency);
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
