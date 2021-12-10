package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import org.fintecy.md.oxr.model.ExchangeRate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

public class OxrModule extends SimpleModule {

    public static final Version VERSION = new Version(1, 0, 1, "SNAPSHOT", "org.fintecy.md", "oxr-client");

    public OxrModule() {
        super(OxrModule.class.getSimpleName(), VERSION,
                Map.of(ExchangeRate.class, ExchangeRateDeserializer.INSTANCE,
                        Instant.class, InstantDeserializer.INSTANT,
                        LocalDate.class, LocalDateDeserializer.INSTANCE));

        addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
    }
}
