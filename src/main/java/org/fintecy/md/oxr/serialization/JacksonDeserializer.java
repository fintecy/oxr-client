package org.fintecy.md.oxr.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JacksonDeserializer implements Deserializer {
    private final ObjectMapper mapper;

    public JacksonDeserializer() {
        this(new ObjectMapper());
    }

    protected JacksonDeserializer(ObjectMapper mapper) {
        this.mapper = mapper.registerModule(new OxrModule());
    }

    public static Deserializer withMapper(ObjectMapper mapper) {
        return new JacksonDeserializer(mapper);
    }

    @Override
    public <T> T deserialize(InputStream json, Class<T> type) throws IOException {
        return mapper.readValue(json, type);
    }
}
