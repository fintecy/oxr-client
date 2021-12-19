package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fintecy.md.oxr.model.Currency;
import org.fintecy.md.oxr.model.ExchangeRate;
import org.fintecy.md.oxr.model.TimeSeriesResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.fintecy.md.oxr.model.Currency.currency;
import static org.fintecy.md.oxr.serialization.ExchangeRateDeserializer.parse;

public class TimeSeriesResponseDeserializer extends StdDeserializer<TimeSeriesResponse> {
    public final static TimeSeriesResponseDeserializer INSTANCE = new TimeSeriesResponseDeserializer();

    public TimeSeriesResponseDeserializer() {
        super(TimeSeriesResponse.class);
    }

    @Override
    public TimeSeriesResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
//        LocalDate start = LocalDate.parse(node.get("start_date").asText());//not used
//        LocalDate end = LocalDate.parse(node.get("end_date").asText());//not used
        Currency base = Currency.currency(node.get("base").asText());

        var rates = new TreeMap<LocalDate, SortedMap<Currency, ExchangeRate>>();
        var fields = node.get("rates").fields();
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
