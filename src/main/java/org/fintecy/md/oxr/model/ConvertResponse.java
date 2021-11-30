package org.fintecy.md.oxr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class ConvertResponse {
    private final ConvertRequest request;
    private final Map<String, Object> meta;
    private final BigDecimal response;

    @JsonCreator
    public ConvertResponse(@JsonProperty("request") ConvertRequest request,
                           @JsonProperty("meta") Map<String, Object> meta,
                           @JsonProperty("response") BigDecimal response,
                           @JsonProperty("disclaimer") String disclaimer,
                           @JsonProperty("license") String license) {
        this.request = request;
        this.meta = meta;
        this.response = response;
    }

    @Override
    public String toString() {
        return "ConvertResponse{" +
                "request=" + request +
                ", meta=" + meta +
                ", response=" + response +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertResponse that = (ConvertResponse) o;
        return Objects.equals(request, that.request) && Objects.equals(meta, that.meta) && Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, meta, response);
    }
}
