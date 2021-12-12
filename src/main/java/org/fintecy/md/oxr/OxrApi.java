package org.fintecy.md.oxr;

import org.fintecy.md.oxr.model.Currency;
import org.fintecy.md.oxr.model.*;
import org.fintecy.md.oxr.requests.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import static org.fintecy.md.oxr.requests.RequestParamsFactory.*;

/**
 * @author batiaev
 * @see <a href="https://docs.openexchangerates.org/docs/api-introduction">docs</a>
 */
public interface OxrApi {
    String ROOT_PATH = "https://openexchangerates.org/api";

    default CompletableFuture<RatesResponse> spot() {
        return spot(RequestParamsFactory.quoteParams().build());
    }

    CompletableFuture<RatesResponse> spot(QuoteRequestParams params);

    /**
     * @return latest rates
     * @see <a href="https://docs.openexchangerates.org/docs/latest-json">docs/latest-json</a>
     */
    default CompletableFuture<RatesResponse> latest() {
        return latest(RequestParamsFactory.quoteParams().build());
    }

    CompletableFuture<RatesResponse> latest(QuoteRequestParams params);

    /**
     * @param date - historical date for rates
     * @return historical rates
     * @see <a href="https://docs.openexchangerates.org/docs/historical-json">docs/historical-json</a>
     */
    default CompletableFuture<RatesResponse> historical(LocalDate date) {
        return historical(RequestParamsFactory.historicalRequest(date).build());
    }

    CompletableFuture<RatesResponse> historical(HistoricalRequestParams params);

    /**
     * @return currencies list
     * @see <a href="https://docs.openexchangerates.org/docs/currencies-json">docs/currencies-json</a>
     */
    default CompletableFuture<CurrenciesResponse> currencies() {
        return currencies(RequestParamsFactory.currenciesRequest());
    }

    CompletableFuture<CurrenciesResponse> currencies(CurrenciesRequestParams params);

    /**
     * @param start - first date of time series
     * @param end   - last date of time series
     * @return time series of rates
     * @see <a href="https://docs.openexchangerates.org/docs/time-series-json">docs/time-series-json</a>
     */
    default CompletableFuture<TimeSeriesResponse> timeSeries(LocalDate start, LocalDate end) {
        return timeSeries(timeSeriesParams(start, end).build());
    }

    CompletableFuture<TimeSeriesResponse> timeSeries(TimeSeriesRequestParams params);

    /**
     * @param value balance to convert
     * @param from  original currency
     * @param to    target currency
     * @return converted balance
     * @see <a href="https://docs.openexchangerates.org/docs/convert">docs/convert</a>
     */
    default CompletableFuture<ConvertResponse> convert(BigDecimal value, Currency from, Currency to) {
        return convert(convertParams(value, from, to));
    }

    CompletableFuture<ConvertResponse> convert(ConvertRequestParams params);

    /**
     * @param params - quotes params
     * @return open/high/low/close rates
     * @see <a href="https://docs.openexchangerates.org/docs/ohlc-json">docs/ohlc-json</a>
     */
    CompletableFuture<OhlcResponse> ohlc(QuoteRequestParams params);

    default CompletableFuture<OhlcResponse> ohlc(Instant start, OxrPeriod period) {
        return ohlc(ohlcParams(start, period).build());
    }

    /**
     * @return usage stats
     * @see <a href="https://docs.openexchangerates.org/docs/usage-json">docs/usage-json</a>
     */
    default CompletableFuture<UsageResponse> usage() {
        return usage(RequestParamsFactory.baseParams());
    }

    CompletableFuture<UsageResponse> usage(BaseRequestParams params);
}
