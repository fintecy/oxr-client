package org.fintecy.md.oxr.requests;

import org.fintecy.md.common.model.Currency;

import java.math.BigDecimal;
import java.util.Objects;

import static org.fintecy.md.oxr.OxrClient.checkRequired;

/**
 * Path Params
 * value:	integerRequired
 * The value to be converted
 * <p>
 * from:	stringRequired
 * The base ('from') currency (3-letter code)
 * <p>
 * to:	stringRequired
 * The target ('to') currency (3-letter code)
 * <p>
 * Query Params
 * app_id:	stringRequired
 * Your unique App ID (required)
 * <p>
 * prettyprint:	booleanOptional
 * Human-readable response for debugging
 */
public class ConvertRequestParams extends BaseRequestParams {
    private final BigDecimal value;
    private final Currency from;
    private final Currency to;

    public ConvertRequestParams(BigDecimal value, Currency from, Currency to, boolean prettyPrint) {
        super(prettyPrint);
        this.value = checkRequired(value, "value should be not 0");
        this.from = checkRequired(from, "from required for convert params");
        this.to = checkRequired(to, "to required for convert params");
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "ConvertRequestParams{" +
                "value=" + value +
                ", from=" + from +
                ", to=" + to +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertRequestParams that = (ConvertRequestParams) o;
        return Objects.equals(value, that.value) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, from, to);
    }

    public static class Builder extends BaseRequestParams.Builder {
        private final BigDecimal value;
        private Currency from;
        private Currency to;

        public Builder(BigDecimal value) {
            this.value = value;
        }

        public Builder(BigDecimal value, Currency from, Currency to) {
            this(value);
            this.from = from;
            this.to = to;
        }

        public Builder from(Currency from) {
            this.from = from;
            return self();
        }

        public Builder to(Currency to) {
            this.to = to;
            return self();
        }

        @Override
        public ConvertRequestParams build() {
            return new ConvertRequestParams(value, from, to, prettyPrint);
        }
    }
}
