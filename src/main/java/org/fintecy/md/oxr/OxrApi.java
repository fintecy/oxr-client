package org.fintecy.md.oxr;

import org.fintecy.md.oxr.model.*;
import org.fintecy.md.oxr.requests.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.concurrent.CompletableFuture;

import static org.fintecy.md.oxr.requests.RequestParamsFactory.*;

/**
 * @link https://docs.openexchangerates.org/docs/api-introduction
 */
public interface OxrApi {
    String ROOT_PATH = "https://openexchangerates.org/api";

    default CompletableFuture<RatesResponse> spot() {
        return spot(RequestParamsFactory.quoteParams().build());
    }

    CompletableFuture<RatesResponse> spot(QuoteRequestParams params);

    /**
     * @link https://docs.openexchangerates.org/docs/latest-json
     */
    default CompletableFuture<RatesResponse> latest() {
        return latest(RequestParamsFactory.quoteParams().build());
    }

    CompletableFuture<RatesResponse> latest(QuoteRequestParams params);

    /**
     * @link https://docs.openexchangerates.org/docs/historical-json
     */
    default CompletableFuture<RatesResponse> historical(LocalDate date) {
        return historical(RequestParamsFactory.historicalRequest(date).build());
    }

    CompletableFuture<RatesResponse> historical(HistoricalRequestParams params);

    /**
     * @link https://docs.openexchangerates.org/docs/currencies-json
     */
    default CompletableFuture<CurrenciesResponse> currencies() {
        return currencies(RequestParamsFactory.currenciesRequest());
    }

    CompletableFuture<CurrenciesResponse> currencies(CurrenciesRequestParams params);

    /**
     * @link https://docs.openexchangerates.org/docs/time-series-json
     */
    default CompletableFuture<TimeSeriesResponse> timeSeries(LocalDate start, LocalDate end) {
        return timeSeries(timeSeriesParams(start, end).build());
    }

    CompletableFuture<TimeSeriesResponse> timeSeries(TimeSeriesRequestParams params);

    /**
     * @link https://docs.openexchangerates.org/docs/convert
     */
    default CompletableFuture<ConvertResponse> convert(double value, Currency from, Currency to) {
        return convert(convertParams(value, from, to));
    }

    CompletableFuture<ConvertResponse> convert(ConvertRequestParams params);

    /**
     * @link https://docs.openexchangerates.org/docs/ohlc-json
     */
    CompletableFuture<OhlcResponse> ohlc(QuoteRequestParams params);

    default CompletableFuture<OhlcResponse> ohlc(Instant start, OxrPeriod period) {
        return ohlc(ohlcParams(start, period).build());
    }

    /**
     * @link https://docs.openexchangerates.org/docs/usage-json
     */
    default CompletableFuture<UsageResponse> usage() {
        return usage(RequestParamsFactory.baseParams());
    }

    CompletableFuture<UsageResponse> usage(BaseRequestParams params);
}
