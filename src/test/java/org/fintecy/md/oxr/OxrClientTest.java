package org.fintecy.md.oxr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.fintecy.md.common.model.Currency;
import org.fintecy.md.oxr.model.*;
import org.fintecy.md.oxr.model.usage.ProductFeatures;
import org.fintecy.md.oxr.model.usage.Usage;
import org.fintecy.md.oxr.model.usage.UsageData;
import org.fintecy.md.oxr.model.usage.UserPlan;
import org.fintecy.md.oxr.requests.OhlcRequestParams;
import org.fintecy.md.oxr.requests.QuoteRequestParams;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.math.BigDecimal.valueOf;
import static java.time.Instant.ofEpochMilli;
import static org.fintecy.md.common.model.Currency.currency;
import static org.fintecy.md.oxr.OxrClient.oxrClient;
import static org.fintecy.md.oxr.model.ExchangeRate.exchangeRate;
import static org.fintecy.md.oxr.requests.RequestParamsFactory.ohlcParams;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(httpPort = 7777)
class OxrClientTest {
    @Test
    void should_fail_auth() {
        //given
        OxrApi api = OxrClient.authorize("INVALID");
        //when
        assertThrows(ExecutionException.class, () -> api.usage().get());
    }

    @Test
    void should_test_historical() throws ExecutionException, InterruptedException {

        //given
        var date = LocalDate.parse("2021-01-01");
        stubFor(get("/historical/" + date.toString() + ".json?prettyprint=0&show_alternative=0")
                .willReturn(aResponse()
                        .withBodyFile("historical.json")));
        var expected = expectedHistoricalResponse(date + "T01:01:01Z");
        //when
        var actual = oxrClient()
                .useClient(HttpClient.newHttpClient())
                .useAuthHeader(true)
                .mapper(new ObjectMapper().registerModule(new JavaTimeModule()))
                .authWith("N/A")
                .rootPath("http://localhost:7777")
                .build()
                .historical(date)
                .get();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_timeSeries() throws ExecutionException, InterruptedException {
        //given
        var start = LocalDate.parse("2021-01-01");
        var end = LocalDate.parse("2021-01-02");
        var api = "time-series.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0")
                .willReturn(aResponse()
                        .withBodyFile(api)));
        var expected = expectedTimeSeriesResponse(start, end);
        //when
        var actual = oxrClient()
                .authWith("N/A")
                .rootPath("http://localhost:7777")
                .build()
                .timeSeries(start, end)
                .get();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_convert_amount() throws ExecutionException, InterruptedException {
        var amount = valueOf(19999.95);
        var from = currency("GBP");
        var to = currency("EUR");
        var timestamp = ofEpochMilli(1449885661);
        var api = "convert";
        stubFor(get("/" + api + "/" + amount + "/" + from.getCode() + "/" + to.getCode() + "?prettyprint=0")
                .willReturn(aResponse()
                        .withBodyFile(api + ".json")));

        var expected = new ConvertResponse(timestamp, from, to, amount, valueOf(27673.975864), valueOf(1.383702));
        //when
        var actual = oxrClient()
                .authWith("N/A")
                .rootPath("http://localhost:7777")
                .build()
                .convert(amount, from, to)
                .get();
        assertEquals(expected, actual);
    }

    @Test
    void should_return_latest() throws ExecutionException, InterruptedException {

        var api = "latest.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0")
                .willReturn(aResponse()
                        .withBodyFile(api)));

        var expected = expectedLatestResponse("2021-12-02T21:00:00Z");
        var actual = oxrClient()
                .authWith("N/A")
                .rootPath("http://localhost:7777")
                .build()
                .latest()
                .get();
        assertEquals(expected, actual);
    }

    @Test
    void should_return_spot() throws ExecutionException, InterruptedException {

        var api = "spot.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0")
                .willReturn(aResponse()
                        .withBodyFile("latest.json")));

        var expected = expectedLatestResponse("2021-12-02T21:00:00Z");
        var actual = oxrClient()
                .authWith("N/A")
                .rootPath("http://localhost:7777")
                .build()
                .spot()
                .get();
        assertEquals(expected, actual);
    }

    @Test
    void should_return_currencies() throws ExecutionException, InterruptedException {

        var api = "currencies.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0")
                .willReturn(aResponse()
                        .withBodyFile(api)));

        var expected = expectedCurrenciesResponse();
        var actual = oxrClient()
                .authWith("N/A")
                .rootPath("http://localhost:7777")
                .build()
                .currencies()
                .get();
        assertEquals("British Pound Sterling", actual.getCurrencyName("GBP"));
        assertTrue(actual.contains("EUR"));
        assertEquals(expected, actual);
    }

    @Test
    void should_return_usage() throws ExecutionException, InterruptedException {
        var api = "usage.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0")
                .willReturn(aResponse()
                        .withBodyFile(api)));

        var appId = "c4623da1b5d94162b3f32a2717d72483";
        var expected = expectedUsageResponse(appId);
        var actual = oxrClient()
                .authWith(appId)
                .rootPath("http://localhost:7777")
                .build()
                .usage()
                .get();
        assertEquals(200, actual.getStatus());
        assertEquals(appId, actual.getData().getAppId());
        assertEquals(expected, actual);
    }

    @Test
    void should_return_ohlc() throws ExecutionException, InterruptedException {
        var api = "ohlc.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0&start_time=2021-01-17T10:23:00Z&period=1d")
                .willReturn(aResponse()
                        .withBodyFile(api)));

        var appId = "c4623da1b5d94162b3f32a2717d72483";
        var start = Instant.parse("2021-01-17T10:23:45.123Z");
        var end = Instant.parse("2021-01-18T10:00:00Z");
        var expected = expectedOhlcResponse(start.truncatedTo(ChronoUnit.MINUTES), end);
        var actual = oxrClient()
                .authWith(appId)
                .rootPath("http://localhost:7777")
                .build()
                .ohlc(start, OxrPeriod.DAY)
                .get();
        assertEquals(actual, expected);
    }

    @Test
    void should_return_ohlc_with_params() throws ExecutionException, InterruptedException {
        var api = "ohlc.json";
        stubFor(get("/" + api + "?prettyprint=0&show_alternative=0&start_time=2021-01-17T10:23:00Z&period=1d")
                .willReturn(aResponse()
                        .withBodyFile(api)));

        var appId = "c4623da1b5d94162b3f32a2717d72483";
        var start = Instant.parse("2021-01-17T10:23:12.123Z");
        var end = Instant.parse("2021-01-18T10:00:00Z");
        var expected = expectedOhlcResponse(start.truncatedTo(ChronoUnit.MINUTES), end);
        OhlcRequestParams.Builder builder = ohlcParams(start, OxrPeriod.DAY)
                .showBidAsk(true)
                .showAlternative(true);
        var actual = oxrClient()
                .authWith(appId)
                .rootPath("http://localhost:7777")
                .build()
                .ohlc(builder.build())
                .get();
        assertEquals(actual, expected);
    }

    private OhlcResponse expectedOhlcResponse(Instant start, Instant end) {
        return new OhlcResponse(start, end, QuoteRequestParams.DEFAULT_BASE_CURRENCY,
                Map.of(
                        currency("EUR"), new CandleStick(
                                valueOf(0.872674),
                                valueOf(0.872674),
                                valueOf(0.87203),
                                valueOf(0.872251),
                                valueOf(0.872253)),
                        currency("GBP"), new CandleStick(
                                valueOf(0.765284),
                                valueOf(0.7657),
                                valueOf(0.7652),
                                valueOf(0.765541),
                                valueOf(0.765503)),
                        currency("HKD"), new CandleStick(
                                valueOf(7.804003),
                                valueOf(7.80568),
                                valueOf(7.80399),
                                valueOf(7.805248),
                                valueOf(7.804725))
                ), "https://openexchangerates.org/terms", "https://openexchangerates.org/license");
    }

    private TimeSeriesResponse expectedTimeSeriesResponse(LocalDate start, LocalDate end) {
        return new TimeSeriesResponse(currency("USD"), new TreeMap<>(Map.of(
                start, new TreeMap<>(Map.of(
                        currency("EUR"), exchangeRate(0.822681),
                        currency("GBP"), exchangeRate(0.73135),
                        currency("RUB"), exchangeRate(73.945)
                )), end, new TreeMap<>(Map.of(
                        currency("EUR"), exchangeRate(0.83),
                        currency("GBP"), exchangeRate(0.74),
                        currency("RUB"), exchangeRate(74)
                )))));
    }

    private RatesResponse expectedHistoricalResponse(String s) {
        return new RatesResponse(Instant.parse(s), QuoteRequestParams.DEFAULT_BASE_CURRENCY.getCode(),
                Map.of(
                        currency("EUR"), exchangeRate(0.822681),
                        currency("GBP"), exchangeRate(0.73135),
                        currency("RUB"), exchangeRate(73.945)
                ));
    }

    private RatesResponse expectedLatestResponse(String s) {
        return new RatesResponse(Instant.parse(s), QuoteRequestParams.DEFAULT_BASE_CURRENCY.getCode(),
                Map.of(
                        currency("EUR"), new ExchangeRate(valueOf(0.885), valueOf(0.885055), valueOf(0.886)),
                        currency("GBP"), exchangeRate(0.751917),
                        currency("RUB"), exchangeRate(73.6685)
                ));
    }

    private CurrenciesResponse expectedCurrenciesResponse() {
        var ccy = new HashMap<Currency, String>();
        ccy.put(currency("BTC"), "Bitcoin");
        ccy.put(currency("ETH"), "Ethereum");
        ccy.put(currency("EUR"), "Euro");
        ccy.put(currency("GBP"), "British Pound Sterling");
        ccy.put(currency("RUB"), "Russian Ruble");
        ccy.put(currency("USD"), "United States Dollar");
        return new CurrenciesResponse(ccy);
    }

    private UsageResponse expectedUsageResponse(String appId) {
        return new UsageResponse(200, new UsageData(appId, "active",
                new UserPlan("Free", "1000 requests / month", "3600s", ProductFeatures.allFeaturesDisabled()),
                new Usage(1, 1000, 999, 28, 2, 0)));
    }
}
