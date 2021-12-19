package org.fintecy.md.oxr.requests;

import org.fintecy.md.oxr.model.Currency;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.fintecy.md.oxr.model.Currency.currency;

/**
 * Query Params
 * app_id:	stringRequired
 * Your unique App ID
 * <p>
 * base:	stringOptional
 * Change base currency (3-letter code, default: USD)
 * <p>
 * symbols:	stringOptional
 * Limit results to specific currencies (comma-separated list of 3-letter codes)
 * <p>
 * prettyprint:	booleanOptional
 * Set to false to reduce response size (removes whitespace)
 * <p>
 * show_alternative:	booleanOptional
 * Extend returned values with alternative, black market and digital currency rates
 */
public class QuoteRequestParams extends CurrenciesRequestParams {
    public final static Currency DEFAULT_BASE_CURRENCY = currency("USD");
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
            params.put("base", base.getCode());
        if (!symbols.isEmpty())
            params.put("symbols", symbols);
        if (showBidAsk)
            params.put("show_bid_ask", 1);
        if (showAlternative)
            params.put("show_alternative", 1);
        //FIXME
//        params.put("prettyprint", prettyPrint ? 1 : 0);
        return params;
    }


    public static class Builder extends CurrenciesRequestParams.Builder {
        protected Currency base = DEFAULT_BASE_CURRENCY;
        protected Collection<Currency> symbols = Set.of();
        protected boolean showBidAsk = false;

        public <T extends Builder> T base(Currency base) {
            this.base = base;
            return self();
        }

        public <T extends Builder> T symbols(Collection<Currency> symbols) {
            this.symbols = symbols;
            return self();
        }

        public <T extends Builder> T showBidAsk(boolean showBidAsk) {
            this.showBidAsk = showBidAsk;
            return self();
        }

        public QuoteRequestParams build() {
            return new QuoteRequestParams(base, symbols, showBidAsk, showAlternative, prettyPrint);
        }
    }
}
