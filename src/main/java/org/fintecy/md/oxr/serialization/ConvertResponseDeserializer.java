package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fintecy.md.oxr.model.ConvertResponse;

import java.io.IOException;
import java.time.Instant;

import static org.fintecy.md.oxr.model.Currency.currency;

public class ConvertResponseDeserializer extends StdDeserializer<ConvertResponse> {
    public final static ConvertResponseDeserializer INSTANCE = new ConvertResponseDeserializer();

    public ConvertResponseDeserializer() {
        super(ConvertResponse.class);
    }

    @Override
    public ConvertResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
        var requestNode = node.get("request");
        var baseAmount = requestNode.get("amount").decimalValue();
        var from = currency(requestNode.get("from").asText());
        var to = currency(requestNode.get("to").asText());

        var metaNode = node.get("meta");
        var timestamp = Instant.ofEpochMilli(metaNode.get("timestamp").longValue());
        var rate = metaNode.get("rate").decimalValue();

        var counterAmount = node.get("response").decimalValue();
        return new ConvertResponse(timestamp, from, to, baseAmount, counterAmount, rate);
    }
}
