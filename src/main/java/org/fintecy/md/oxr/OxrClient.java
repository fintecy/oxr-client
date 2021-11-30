package org.fintecy.md.oxr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.fintecy.md.oxr.model.*;
import org.fintecy.md.oxr.requests.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.Optional.ofNullable;

public class OxrClient implements OxrApi {
    private final String rootPath;
    private final String token;
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final boolean useAuthHeader;

    public OxrClient(String rootPath, String token, boolean useAuthHeader, ObjectMapper mapper, HttpClient httpClient) {
        this.token = checkRequired(token, "Auth token not provided for OXR client!");
        this.client = checkRequired(httpClient, "Http client required for OXR client");
        this.useAuthHeader = useAuthHeader;
        this.mapper = mapper;
        this.rootPath = rootPath;
    }

    public static OxrApi authorize(String token) {
        return oxrClient().authWith(token).build();
    }

    public static OxrClient.Builder oxrClient() {
        return new OxrClient.Builder();
    }

    public static double checkRequired(double v, String msg) {
        return (v == 0 ? Optional.<Double>empty() : Optional.of(v))
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    public static <T> T checkRequired(T v, String msg) {
        return ofNullable(v)
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    @Override
    public CompletableFuture<RatesResponse> spot(QuoteRequestParams params) {
        return processRequest("/spot.json", params, RatesResponse.class);
    }

    @Override
    public CompletableFuture<RatesResponse> latest(QuoteRequestParams params) {
        return processRequest("/latest.json", params, RatesResponse.class);
    }

    @Override
    public CompletableFuture<RatesResponse> historical(HistoricalRequestParams params) {
        return processRequest("/historical/" + params.getDate() + ".json", params, RatesResponse.class);
    }

    @Override
    public CompletableFuture<CurrenciesResponse> currencies(CurrenciesRequestParams params) {
        return processRequest("/currencies.json", params, CurrenciesResponse.class);
    }

    @Override
    public CompletableFuture<UsageResponse> usage(BaseRequestParams params) {
        return processRequest("/usage.json", params, UsageResponse.class);
    }

    @Override
    public CompletableFuture<TimeSeriesResponse> timeSeries(TimeSeriesRequestParams params) {
        return processRequest("/time-series.json", params, TimeSeriesResponse.class);
    }

    @Override
    public CompletableFuture<ConvertResponse> convert(ConvertRequestParams params) {
        return processRequest(format("/convert/%s/%s/%s",
                        params.getValue(), params.getFrom().getCurrencyCode(), params.getTo().getCurrencyCode()),
                params, ConvertResponse.class);
    }

    @Override
    public CompletableFuture<OhlcResponse> ohlc(QuoteRequestParams params) {
        return processRequest(getRequest("/ohlc.json", params), OhlcResponse.class);
    }

    private <T> CompletableFuture<T> processRequest(String url, BaseRequestParams params, Class<T> responseType) {
        return processRequest(getRequest(url, params), responseType);
    }

    private <T> CompletableFuture<T> processRequest(HttpRequest request, Class<T> responseType) {
        return client.sendAsync(request, ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> parseResponse(s, responseType));
    }

    private <T> T parseResponse(String body, Class<T> modelClass) {
        try {
            return mapper.readValue(body, modelClass);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Can parse response", e);
        }
    }

    private HttpRequest getRequest(String path, BaseRequestParams params) {
        var builder = HttpRequest.newBuilder()
                .uri(buildPath(path, params));
        if (useAuthHeader) {
            builder = builder.header("Authorization", "Token " + token);
        }
        return builder.build();
    }

    private URI buildPath(String path, BaseRequestParams params) {
        Map<String, Object> queryParams = params.toMap();
        boolean hasParams = !useAuthHeader;
        var urlBuilder = new StringBuilder(rootPath + path + (useAuthHeader ? "" : "?app_id=" + token));
        for (String key : queryParams.keySet()) {
            urlBuilder.append(hasParams ? "&" : "?").append(key).append("=").append(queryParams.get(key));
            if (!hasParams) {
                hasParams = true;
            }
        }
        return URI.create(urlBuilder.toString());
    }

    static class Builder {
        private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        private HttpClient client = HttpClient.newHttpClient();
        private boolean useAuthHeader = true;
        private String token;
        private String rootPath = ROOT_PATH;

        public Builder useClient(HttpClient client) {
            this.client = client;
            return this;
        }

        public Builder objectMapper(ObjectMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public Builder rootPath(String rootPath) {
            this.rootPath = rootPath;
            return this;
        }

        public Builder authWith(String token) {
            this.token = token;
            return this;
        }

        public Builder useAuthHeader(boolean useAuthHeader) {
            this.useAuthHeader = useAuthHeader;
            return this;
        }

        public OxrApi build() {
            return new OxrClient(rootPath, token, useAuthHeader, mapper, client);
        }
    }
}
