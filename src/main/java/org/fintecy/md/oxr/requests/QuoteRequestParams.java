package org.fintecy.md.oxr.requests;

import java.util.Collection;
import java.util.Currency;
import java.util.Map;
import java.util.Set;

/**
 * Query Params
 * app_id:	stringRequired
 * Your unique App ID
 *
 * base:	stringOptional
 * Change base currency (3-letter code, default: USD)
 *
 * symbols:	stringOptional
 * Limit results to specific currencies (comma-separated list of 3-letter codes)
 *
 * prettyprint:	booleanOptional
 * Set to false to reduce response size (removes whitespace)
 *
 * show_alternative:	booleanOptional
 * Extend returned values with alternative, black market and digital currency rates
 */
public class QuoteRequestParams extends CurrenciesRequestParams {
    public final static Currency DEFAULT_BASE_CURRENCY = Currency.getInstance("USD");
    protected final Currency base;
    protected final Collection<Currency> symbols;
    protected final boolean showBidAsk;

    public QuoteRequestParams(Currency base, Collection<Currency> symbols,
                              boolean showBidAsk, boolean showAlternative, boolean prettyPrint) {
        super(showAlternative, false, prettyPrint);
        this.showBidAsk = showBidAsk;
        this.base = base;
        this.symbols = symbols;
    }

    public Map<String, Object> toMap() {
        var params = super.toMap();
        params.remove("show_inactive");
        if (!base.equals(DEFAULT_BASE_CURRENCY))
            params.put("base", base.getCurrencyCode());
        if (!symbols.isEmpty())
            params.put("symbols", symbols);
        //FIXME
//        params.put("show_bid_ask", showBidAsk ? 1 : 0);
//        params.put("show_alternative", showAlternative ? 1 : 0);
//        params.put("prettyprint", prettyPrint ? 1 : 0);
        return params;
    }


    public static class Builder extends CurrenciesRequestParams.Builder {
        protected Currency base = DEFAULT_BASE_CURRENCY;
        protected Collection<Currency> symbols = Set.of();
        protected boolean showBidAsk = false;

        public Builder base(Currency base) {
            this.base = base;
            return self();
        }

        public Builder symbols(Collection<Currency> symbols) {
            this.symbols = symbols;
            return self();
        }

        public Builder showBidAsk(boolean showBidAsk) {
            this.showBidAsk = showBidAsk;
            return self();
        }

        public QuoteRequestParams build() {
            return new QuoteRequestParams(base, symbols, showBidAsk, showAlternative, prettyPrint);
        }
    }
}
