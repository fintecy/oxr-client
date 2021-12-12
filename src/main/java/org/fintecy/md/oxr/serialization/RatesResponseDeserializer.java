package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fintecy.md.oxr.model.Currency;
import org.fintecy.md.oxr.model.ExchangeRate;
import org.fintecy.md.oxr.model.RatesResponse;

import java.io.IOException;
import java.util.HashMap;

import static java.time.Instant.ofEpochSecond;
import static org.fintecy.md.oxr.model.Currency.currency;
import static org.fintecy.md.oxr.serialization.ExchangeRateDeserializer.parse;

public class RatesResponseDeserializer extends StdDeserializer<RatesResponse> {
    public final static RatesResponseDeserializer INSTANCE = new RatesResponseDeserializer();

    public RatesResponseDeserializer() {
        super(RatesResponse.class);
    }

    @Override
    public RatesResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
        var timestamp = ofEpochSecond(node.get("timestamp").longValue());
        var base = node.get("base").asText();
        var ratesNode = node.get("rates");
        var rates = new HashMap<Currency, ExchangeRate>();
        var fields = ratesNode.fields();
        while (fields.hasNext()) {
            var entry = fields.next();
            var currency = currency(entry.getKey());
            var rate = parse(jp, entry.getValue());
            rates.put(currency, rate);
        }
        return new RatesResponse(timestamp, base, rates);
    }
}
