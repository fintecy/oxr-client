package org.fintecy.md.oxr.model;

public class Currency extends MicroType<String> {

    public Currency(String code) {
        super(code);
    }

    public static Currency currency(String code) {
        return new Currency(code);
    }

    public static Currency fromJavaCurrency(java.util.Currency currency) {
        return currency(currency.getCurrencyCode());
    }

    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(getValue());
    }
}
