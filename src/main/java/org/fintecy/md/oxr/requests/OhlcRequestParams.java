package org.fintecy.md.oxr.requests;

import org.fintecy.md.common.model.Currency;
import org.fintecy.md.oxr.model.OxrPeriod;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Query Params
 * app_id:	stringRequired
 * Your unique App ID.
 * <p>
 * start_time:	datetimeRequired
 * The start time for the requested OHLC period (ISO-8601 format, UTC only). Restrictions apply (please see below).
 * <p>
 * period:	stringRequired
 * The requested period (starting on the start_time), e.g. "1m", "30m", "1d". Please see below for supported OHLC periods.
 * <p>
 * symbols:	stringRecommended
 * Limit results to specific currencies (comma-separated list of 3-letter codes)
 * <p>
 * base:	stringOptional
 * Change base currency (3-letter code, default: USD)
 * <p>
 * prettyprint:	booleanOptional
 * Human-readable response for debugging (response size will be much larger)
 */
public class OhlcRequestParams extends QuoteRequestParams {
    private final Instant start;
    private final OxrPeriod period;

    public OhlcRequestParams(Instant start, OxrPeriod period,
                             Currency base, Collection<Currency> symbols, boolean showBidAsk, boolean showAlternative,
                             boolean prettyPrint) {
        super(base, symbols, showBidAsk, showAlternative, prettyPrint);
        this.start = start;
        this.period = period;
    }

    @Override
    public Map<String, Object> toMap() {
        var map = super.toMap();
        map.put("start", start.truncatedTo(ChronoUnit.MINUTES));
        map.put("period", period.getCode());
        return map;
    }

    @Override
    public String toString() {
        return "OhlcRequestParams{" +
                "start=" + start +
                ", period=" + period +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OhlcRequestParams that = (OhlcRequestParams) o;
        return Objects.equals(start, that.start) && period == that.period;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, period);
    }

    public static class Builder extends QuoteRequestParams.Builder {
        private final Instant start;
        private final OxrPeriod period;

        public Builder(Instant start, OxrPeriod period) {
            this.start = start;
            this.period = period;
        }

        @Override
        public QuoteRequestParams build() {
            return new OhlcRequestParams(start, period, base, symbols, showBidAsk, showAlternative, prettyPrint);
        }
    }
}
