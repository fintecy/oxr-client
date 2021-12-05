package org.fintecy.md.oxr;

public interface Deserializer {
    <T> T deserialize(String json, Class<T> type);
}