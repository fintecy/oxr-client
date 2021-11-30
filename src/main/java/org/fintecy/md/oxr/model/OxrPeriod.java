package org.fintecy.md.oxr.model;

import java.time.Duration;
import java.time.Period;

public enum OxrPeriod {
    MIN_1("1m"),
    MIN_5("5m"),
    MIN_15("15m"),
    MIN_30("30m"),
    HOUR_1("1h"),
    HOUR_12("12h"),
    DAY("1d"),
    WEEK("1w"),
    MONTH("1mo");

    private String code;

    public static OxrPeriod oxrPeriod(Period period) {
        String s = period.toString();
        switch (s) {
            case "P1D":
                return DAY;
            case "P7D":
                return WEEK;
            case "P1M":
                return MONTH;
            default:
                throw new IllegalStateException("Invalid OXR period: " + s);
        }
    }

    public static OxrPeriod oxrPeriod(Duration duration) {
        String s = duration.toString();
        switch (s) {
            case "PT24H":
            case "PT1440M":
            case "PT86400S":
                return DAY;
            case "PT12H":
            case "PT720M":
            case "PT43200S":
                return HOUR_12;
            case "PT1H":
            case "PT60M":
            case "PT3600S":
                return HOUR_1;
            case "PT30M":
            case "PT1800S":
                return MIN_30;
            case "PT15M":
            case "PT900S":
                return MIN_15;
            case "PT5M":
            case "PT300S":
                return MIN_5;
            case "PT1M":
            case "PT60S":
                return MIN_1;
            default:
                throw new IllegalStateException("Invalid OXR duration: " + s);
        }
    }

    OxrPeriod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public long toSeconds() {
        switch (this) {
            case MIN_1:
                return 60;
            case MIN_5:
                return 5 * 60;
            case MIN_15:
                return 15 * 60;
            case MIN_30:
                return 30 * 60;
            case HOUR_1:
                return 60 * 60;
            case HOUR_12:
                return 12 * 60 * 60;
            case DAY:
                return 24 * 60 * 60;
            case WEEK:
                return 7 * 24 * 60 * 60;
            case MONTH:
                return 30 * 24 * 60 * 60;
            default:
                return 1;//this should never happens
        }
    }
}
