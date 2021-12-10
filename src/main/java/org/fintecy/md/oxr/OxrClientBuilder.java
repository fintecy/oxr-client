package org.fintecy.md.oxr;

import org.fintecy.md.oxr.serialization.Deserializer;
import org.fintecy.md.oxr.serialization.JacksonDeserializer;

import java.net.http.HttpClient;

class OxrClientBuilder {
    private Deserializer deserializer = new JacksonDeserializer();
    private HttpClient client = HttpClient.newHttpClient();
    private boolean useAuthHeader = true;
    private String token;
    private String rootPath = OxrApi.ROOT_PATH;

    public OxrClientBuilder useClient(HttpClient client) {
        this.client = client;
        return this;
    }

    public OxrClientBuilder deserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
        return this;
    }

    public OxrClientBuilder rootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public OxrClientBuilder authWith(String token) {
        this.token = token;
        return this;
    }

    public OxrClientBuilder useAuthHeader(boolean useAuthHeader) {
        this.useAuthHeader = useAuthHeader;
        return this;
    }

    public OxrApi build() {
        return new OxrClient(rootPath, token, useAuthHeader, deserializer, client);
    }
}
