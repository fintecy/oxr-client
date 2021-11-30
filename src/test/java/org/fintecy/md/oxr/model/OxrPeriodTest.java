package org.fintecy.md.oxr.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.Duration;
import java.time.Period;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OxrPeriodTest {

    @Test
    void should_parse_1d_period() {
        //given
        var period = Period.ofDays(1);
        //when
        var oxrPeriod = OxrPeriod.oxrPeriod(period);
        //then
        assertEquals(oxrPeriod, OxrPeriod.DAY);
    }

    @ParameterizedTest
    @EnumSource(OxrPeriod.class)
    void should_convert_to_seconds_properly(OxrPeriod period) {
        //given
        var expected = new HashMap<OxrPeriod, Integer>();
        expected.put(OxrPeriod.MIN_1, 60);
        expected.put(OxrPeriod.MIN_5, 5 * 60);
        expected.put(OxrPeriod.MIN_15, 15 * 60);
        expected.put(OxrPeriod.MIN_30, 30 * 60);
        expected.put(OxrPeriod.HOUR_1, 60 * 60);
        expected.put(OxrPeriod.HOUR_12, 12 * 60 * 60);
        expected.put(OxrPeriod.DAY, 24 * 60 * 60);
        expected.put(OxrPeriod.WEEK, 7 * 24 * 60 * 60);
        expected.put(OxrPeriod.MONTH, 30 * 24 * 60 * 60);
        //when
        var seconds = period.toSeconds();
        //then
        assertEquals(seconds, (long) expected.get(period));
    }

    @Test
    void should_parse_12h_duration() {
        //given
        var period = Duration.ofHours(12);
        //when
        var oxrPeriod = OxrPeriod.oxrPeriod(period);
        //then
        assertEquals(oxrPeriod, OxrPeriod.HOUR_12);
    }
}