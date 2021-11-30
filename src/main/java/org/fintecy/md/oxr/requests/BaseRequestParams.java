package org.fintecy.md.oxr.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Query Params
 * app_id:	stringRequired
 * Your unique App ID (required)
 * <p>
 * prettyprint:	booleantrue
 * Set 'false' to minify response
 */
public class BaseRequestParams {
    protected final boolean prettyPrint;

    public BaseRequestParams(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public Map<String, Object> toMap() {
        var params = new HashMap<String, Object>();
        params.put("prettyprint", prettyPrint ? 1 : 0);
        return params;
    }

    @Override
    public String toString() {
        return "BaseRequestParams{" +
                "prettyPrint=" + prettyPrint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseRequestParams that = (BaseRequestParams) o;
        return prettyPrint == that.prettyPrint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prettyPrint);
    }

    public static class Builder {
        protected boolean prettyPrint = false;

        public Builder enablePrettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            return self();
        }

        <T> T self() {
            return (T) this;
        }

        public <T extends BaseRequestParams> T build() {
            return (T) new BaseRequestParams(prettyPrint);
        }
    }
}
