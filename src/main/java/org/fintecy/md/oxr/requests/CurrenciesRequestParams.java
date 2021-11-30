package org.fintecy.md.oxr.requests;

import java.util.Map;
import java.util.Objects;

/**
 * Query Params
 * prettyprint:	boolean1
 * show_alternative:	boolean0
 * Include alternative currencies.
 *
 * show_inactive:	boolean0
 * Include historical/inactive currencies
 */
public class CurrenciesRequestParams extends BaseRequestParams {
    protected final boolean showInactive;
    protected final boolean showAlternative;

    public CurrenciesRequestParams(boolean showAlternative, boolean showInactive, boolean prettyPrint) {
        super(prettyPrint);
        this.showInactive = showInactive;
        this.showAlternative = showAlternative;
    }

    public Map<String, Object> toMap() {
        var params = super.toMap();
        params.put("show_alternative", showAlternative ? 1 : 0);
        params.put("show_inactive", showInactive ? 1 : 0);
        return params;
    }

    public boolean isShowInactive() {
        return showInactive;
    }

    public boolean isShowAlternative() {
        return showAlternative;
    }

    @Override
    public String toString() {
        return "CurrenciesRequestParams{" +
                "showInactive=" + showInactive +
                ", showAlternative=" + showAlternative +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurrenciesRequestParams that = (CurrenciesRequestParams) o;
        return showInactive == that.showInactive && showAlternative == that.showAlternative;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), showInactive, showAlternative);
    }

    static class Builder extends BaseRequestParams.Builder {
        protected boolean showInactive;
        protected boolean showAlternative;

        public Builder showInactive(boolean showInactive) {
            this.showInactive = showInactive;
            return self();
        }

        public Builder showAlternative(boolean showAlternative) {
            this.showAlternative = showAlternative;
            return self();
        }

        @Override
        public CurrenciesRequestParams build() {
            return new CurrenciesRequestParams(showAlternative, prettyPrint, showInactive);
        }
    }

}
