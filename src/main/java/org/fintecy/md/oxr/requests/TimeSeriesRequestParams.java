package org.fintecy.md.oxr.requests;

import org.fintecy.md.oxr.model.Currency;

import java.time.LocalDate;
import java.util.Collection;

/**
 * start:	yyyy-mm-dd Required
 * The time series start date in YYYY-MM-DD format
 *
 * end:	yyyy-mm-dd Required
 * The time series end date in YYYY-MM-DD format (see notes)
 *
 * symbols:	string Recommended
 * Limit results to specific currencies (comma-separated list of 3-letter codes)
 *
 * base:	string Optional
 * Change base currency (3-letter code, default: USD)
 *
 * show_alternative:	boolean Optional
 * Extend returned values with alternative, black market and digital currency rates
 *
 * prettyprint:	boolean Optional
 * Human-readable response for debugging (response size will be much larger)
 */
public class TimeSeriesRequestParams extends QuoteRequestParams {
    private final LocalDate start;
    private final LocalDate end;

    public TimeSeriesRequestParams(LocalDate start, LocalDate end,
                                   Currency base, Collection<Currency> symbols,
                                   boolean showBidAsk, boolean showAlternative, boolean prettyPrint) {
        super(base, symbols, showBidAsk, showAlternative, prettyPrint);
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public static class Builder extends QuoteRequestParams.Builder {
        private final LocalDate start;
        private final LocalDate end;

        Builder(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public TimeSeriesRequestParams build() {
            return new TimeSeriesRequestParams(start, end, base, symbols, showBidAsk, showAlternative, prettyPrint);
        }
    }
}
