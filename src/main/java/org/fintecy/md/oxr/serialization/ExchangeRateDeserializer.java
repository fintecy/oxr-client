package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.fintecy.md.oxr.model.ExchangeRate;

import java.io.IOException;
import java.math.BigDecimal;

import static org.fintecy.md.oxr.model.ExchangeRate.exchangeRate;

public class ExchangeRateDeserializer extends StdDeserializer<ExchangeRate> {
    public final static ExchangeRateDeserializer INSTANCE = new ExchangeRateDeserializer();

    public ExchangeRateDeserializer() {
        this(ExchangeRate.class);
    }

    protected ExchangeRateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ExchangeRate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
        final var isDecimal = node.isBigDecimal() || node.isDouble();
        if (isDecimal) {
            final var mid = node.decimalValue();
            return exchangeRate(mid);
        } else {
            if (!node.has("mid"))
                throw new InvalidFormatException(jp, "mid rate not present", node, BigDecimal.class);
            final var mid = node.get("mid").decimalValue();
            if (!node.has("ask") && !node.has("bid"))
                return exchangeRate(mid);
            final var ask = node.has("ask") ? node.get("ask").decimalValue() : mid;
            final var bid = node.has("bid") ? node.get("bid").decimalValue() : mid;
            return new ExchangeRate(ask, mid, bid);
        }
    }
}
