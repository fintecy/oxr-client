package org.fintecy.md.oxr.requests;

import org.fintecy.md.common.model.Currency;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

/**
 * Path Params
 * date:	yyyy-mm-ddRequired
 * The requested date in YYYY-MM-DD format (required).
 * <p>
 * Query Params
 * app_id:	stringRequired
 * Your unique App ID (required)
 * <p>
 * base:	stringOptional
 * Change base currency (3-letter code, default: USD)
 * <p>
 * symbols:	stringOptional
 * Limit results to specific currencies (comma-separated list of 3-letter codes)
 * <p>
 * show_alternative:	boolean(Optional)
 * Extend returned values with alternative, black market and digital currency rates
 * <p>
 * prettyprint:	boolean(Optional)
 * Human-readable response for debugging (response size will be much larger)
 */
public class HistoricalRequestParams extends QuoteRequestParams {
    private final LocalDate date;

    public HistoricalRequestParams(LocalDate date, Currency base, Collection<Currency> symbols,
                                   boolean showBidAsk, boolean showAlternative, boolean prettyPrint) {
        super(base, symbols, showBidAsk, showAlternative, prettyPrint);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "HistoricalRequestParams{" +
                "date=" + date +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HistoricalRequestParams that = (HistoricalRequestParams) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }

    public HistoricalRequestParams withDate(LocalDate date) {
        return new HistoricalRequestParams(date, base, symbols, showBidAsk, showAlternative, prettyPrint);
    }

    public static class Builder extends QuoteRequestParams.Builder {
        private final LocalDate date;

        Builder(LocalDate date) {
            this.date = date;
        }

        @Override
        public HistoricalRequestParams build() {
            return new HistoricalRequestParams(date, base, symbols, showBidAsk, showAlternative, prettyPrint);
        }
    }
}
