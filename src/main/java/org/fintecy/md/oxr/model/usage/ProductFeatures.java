package org.fintecy.md.oxr.model.usage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProductFeatures {
    private final boolean base;
    private final boolean symbols;
    private final boolean experimental;
    private final boolean timeSeries;
    private final boolean convert;
    private final boolean bidAsk;
    private final boolean ohlc;
    private final boolean spot;

    @JsonCreator
    public ProductFeatures(@JsonProperty("base") boolean base,
                           @JsonProperty("symbols") boolean symbols,
                           @JsonProperty("experimental") boolean experimental,
                           @JsonProperty("time-series") boolean timeSeries,
                           @JsonProperty("convert") boolean convert,
                           @JsonProperty("bid-ask") boolean bidAsk,
                           @JsonProperty("ohlc") boolean ohlc,
                           @JsonProperty("spot") boolean spot) {
        this.base = base;
        this.symbols = symbols;
        this.experimental = experimental;
        this.timeSeries = timeSeries;
        this.convert = convert;
        this.bidAsk = bidAsk;
        this.ohlc = ohlc;
        this.spot = spot;
    }

    public static ProductFeatures allFeaturesDisabled() {
        return new ProductFeatures(false, false, false, false,
                false, false, false, false);
    }

    public boolean isBase() {
        return base;
    }

    public boolean isSymbols() {
        return symbols;
    }

    public boolean isExperimental() {
        return experimental;
    }

    public boolean isTimeSeries() {
        return timeSeries;
    }

    public boolean isConvert() {
        return convert;
    }

    public boolean isBidAsk() {
        return bidAsk;
    }

    public boolean isOhlc() {
        return ohlc;
    }

    public boolean isSpot() {
        return spot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductFeatures)) return false;
        ProductFeatures that = (ProductFeatures) o;
        return base == that.base && symbols == that.symbols && experimental == that.experimental
                && timeSeries == that.timeSeries && convert == that.convert && bidAsk == that.bidAsk
                && ohlc == that.ohlc && spot == that.spot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, symbols, experimental, timeSeries, convert, bidAsk, ohlc, spot);
    }

    @Override
    public String toString() {
        return "ProductFeatures{" +
                "base=" + base +
                ", symbols=" + symbols +
                ", experimental=" + experimental +
                ", timeSeries=" + timeSeries +
                ", convert=" + convert +
                ", bidAsk=" + bidAsk +
                ", ohlc=" + ohlc +
                ", spot=" + spot +
                '}';
    }
}
