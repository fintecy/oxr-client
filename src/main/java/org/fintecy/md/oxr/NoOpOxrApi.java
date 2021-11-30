package org.fintecy.md.oxr;

import org.fintecy.md.oxr.model.*;
import org.fintecy.md.oxr.requests.*;

import java.util.concurrent.CompletableFuture;

/**
 * Available for testing purposes
 */
public class NoOpOxrApi implements OxrApi {
    @Override
    public CompletableFuture<RatesResponse> spot(QuoteRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<RatesResponse> latest(QuoteRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<RatesResponse> historical(HistoricalRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<CurrenciesResponse> currencies(CurrenciesRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<TimeSeriesResponse> timeSeries(TimeSeriesRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<ConvertResponse> convert(ConvertRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<OhlcResponse> ohlc(QuoteRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }

    @Override
    public CompletableFuture<UsageResponse> usage(BaseRequestParams params) {
        return CompletableFuture.failedFuture(new IllegalStateException("not implemented"));
    }
}
