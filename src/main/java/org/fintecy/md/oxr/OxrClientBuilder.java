package org.fintecy.md.oxr;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.failsafe.Policy;
import org.fintecy.md.oxr.serialization.OxrModule;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

class OxrClientBuilder {
    private ObjectMapper mapper = new ObjectMapper().registerModule(new OxrModule());
    private HttpClient client = HttpClient.newHttpClient();
    private List<Policy<Object>> policies = new ArrayList<>();
    private boolean useAuthHeader = true;
    private String token;
    private String rootPath = OxrApi.ROOT_PATH;

    public OxrClientBuilder useClient(HttpClient client) {
        this.client = client;
        return this;
    }

    public OxrClientBuilder mapper(ObjectMapper mapper) {
        this.mapper = mapper.registerModule(new OxrModule());
        return this;
    }

    public OxrClientBuilder rootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public OxrClientBuilder with(Policy<Object> policy) {
        this.policies.add(policy);
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
        return new OxrClient(rootPath, token, useAuthHeader, mapper, client, policies);
    }
}
