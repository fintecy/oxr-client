package org.fintecy.md.oxr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonDeserializer implements Deserializer {
    private final ObjectMapper mapper;

    public JacksonDeserializer() {
        this(new ObjectMapper().registerModule(new JavaTimeModule()));
    }

    protected JacksonDeserializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static Deserializer withMapper(ObjectMapper mapper) {
        return new JacksonDeserializer(mapper);
    }

    @Override
    public <T> T deserialize(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Can parse response", e);
        }
    }
}
