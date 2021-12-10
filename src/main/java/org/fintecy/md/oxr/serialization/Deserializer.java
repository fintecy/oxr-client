package org.fintecy.md.oxr.serialization;

import java.io.IOException;
import java.io.InputStream;

public interface Deserializer {
    <T> T deserialize(InputStream json, Class<T> type) throws IOException;
}