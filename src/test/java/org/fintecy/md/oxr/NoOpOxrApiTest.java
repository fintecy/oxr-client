package org.fintecy.md.oxr;

import org.fintecy.md.oxr.model.OxrPeriod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

import static java.math.BigDecimal.ONE;
import static java.time.LocalDate.now;
import static org.fintecy.md.oxr.model.Currency.currency;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NoOpOxrApiTest {
    private OxrApi noOpOxrApi;

    @BeforeEach
    void setUp() {
        noOpOxrApi = new NoOpOxrApi();
    }

    @Test()
    void should_throw_exception_for_spot() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.spot().get());
    }

    @Test
    void should_throw_exception_for_latest() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.latest().get());
    }

    @Test
    void should_throw_exception_for_historical() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.historical(now()).get());
    }

    @Test
    void should_throw_exception_for_currencies() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.currencies().get());
    }

    @Test
    void should_throw_exception_for_timeSeries() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.timeSeries(now().minusWeeks(1), now()).get());
    }

    @Test
    void should_throw_exception_for_convert() {
        //given
        var USD = currency("USD");
        var GBP = currency("GBP");
        //when then
        assertThrows(ExecutionException.class, () -> noOpOxrApi.convert(ONE, USD, GBP).get());
    }

    @Test
    void should_throw_exception_for_ohlc() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.ohlc(Instant.now(), OxrPeriod.DAY).get());
    }

    @Test
    void should_throw_exception_for_usage() {
        assertThrows(ExecutionException.class, () -> noOpOxrApi.usage().get());
    }
}