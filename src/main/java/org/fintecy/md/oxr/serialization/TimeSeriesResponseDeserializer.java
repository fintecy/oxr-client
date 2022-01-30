package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fintecy.md.oxr.model.Currency;
import org.fintecy.md.oxr.model.ExchangeRate;
import org.fintecy.md.oxr.model.TimeSeriesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Collections.emptyIterator;
import static java.util.Optional.ofNullable;
import static org.fintecy.md.oxr.model.Currency.currency;
import static org.fintecy.md.oxr.requests.QuoteRequestParams.DEFAULT_BASE_CURRENCY;
import static org.fintecy.md.oxr.serialization.ExchangeRateDeserializer.parse;

public class TimeSeriesResponseDeserializer extends StdDeserializer<TimeSeriesResponse> {
    private final static Logger LOG = LoggerFactory.getLogger(TimeSeriesResponseDeserializer.class);
    public final static TimeSeriesResponseDeserializer INSTANCE = new TimeSeriesResponseDeserializer();

    public TimeSeriesResponseDeserializer() {
        super(TimeSeriesResponse.class);
    }

    @Override
    public TimeSeriesResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
//        final var start = ofNullable(node.get("start_date"))
//                .map(JsonNode::asText)
//                .map(LocalDate::parse)
//                .orElse(LocalDate.now());//not used
//        final var end = ofNullable(node.get("end_date"))
//                .map(JsonNode::asText)
//                .map(LocalDate::parse)
//                .orElse(LocalDate.now());//not used
        final var base = ofNullable(node.get("base"))
                .map(JsonNode::asText)
                .map(Currency::currency)
                .orElse(DEFAULT_BASE_CURRENCY);

        var rates = new TreeMap<LocalDate, SortedMap<Currency, ExchangeRate>>();
        var fields = ofNullable(node.get("rates"))
                .map(JsonNode::fields)
                .orElse(emptyIterator());
        if (!fields.hasNext()) {
            LOG.error("Not rates provided in response");
        }
        while (fields.hasNext()) {
            var entry = fields.next();
            var date = LocalDate.parse(entry.getKey());
            if (!rates.containsKey(date))
                rates.put(date, new TreeMap<>());

            var dateRatesMap = rates.get(date);
            var dateRateNodes = entry.getValue().fields();
            while (dateRateNodes.hasNext()) {
                var e = dateRateNodes.next();
                var ccy = currency(e.getKey());
                var rate = parse(jp, e.getValue());
                dateRatesMap.put(ccy, rate);
            }
        }
        return new TimeSeriesResponse(base, rates);
    }
}
