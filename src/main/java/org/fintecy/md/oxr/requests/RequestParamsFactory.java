package org.fintecy.md.oxr.requests;

import org.fintecy.md.common.model.Currency;
import org.fintecy.md.oxr.model.OxrPeriod;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class RequestParamsFactory {

    public static TimeSeriesRequestParams.Builder timeSeriesParams(LocalDate start, LocalDate end) {
        return new TimeSeriesRequestParams.Builder(start, end);
    }

    public static QuoteRequestParams.Builder quoteParams() {
        return new QuoteRequestParams.Builder();
    }

    public static HistoricalRequestParams.Builder historicalRequest(LocalDate date) {
        return new HistoricalRequestParams.Builder(date);
    }

    public static CurrenciesRequestParams currenciesRequest() {
        return new CurrenciesRequestParams.Builder().build();
    }

    public static CurrenciesRequestParams.Builder currenciesRequest(boolean showInactive) {
        return new CurrenciesRequestParams.Builder().showInactive(showInactive);
    }

    public static QuoteRequestParams.Builder withBase(Currency base) {
        return new QuoteRequestParams.Builder().base(base);
    }

    public static ConvertRequestParams.Builder convertAmount(BigDecimal value) {
        return new ConvertRequestParams.Builder(value);
    }

    public static ConvertRequestParams convertParams(BigDecimal value, Currency from, Currency to) {
        return new ConvertRequestParams.Builder(value, from, to).build();
    }

    public static BaseRequestParams baseParams() {
        return new BaseRequestParams.Builder().build();
    }

    public static QuoteRequestParams.Builder ohlcParams(Instant start, OxrPeriod period) {
        return new OhlcRequestParams.Builder(start, period);
    }

}
