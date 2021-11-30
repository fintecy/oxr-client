package org.fintecy.md.oxr.model.usage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UserPlan {
    private final String name;
    private final String quota;
    private final String updateFrequency;
    private final ProductFeatures features;

    @JsonCreator
    public UserPlan(@JsonProperty("name") String name,
                    @JsonProperty("quota") String quota,
                    @JsonProperty("update_frequency") String updateFrequency,
                    @JsonProperty("features") ProductFeatures features) {
        this.name = name;
        this.quota = quota;
        this.updateFrequency = updateFrequency;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public String getQuota() {
        return quota;
    }

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public ProductFeatures getFeatures() {
        return features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPlan)) return false;
        UserPlan userPlan = (UserPlan) o;
        return Objects.equals(name, userPlan.name) && Objects.equals(quota, userPlan.quota) && Objects.equals(updateFrequency, userPlan.updateFrequency) && Objects.equals(features, userPlan.features);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quota, updateFrequency, features);
    }

    @Override
    public String toString() {
        return "UserPlan{" +
                "name='" + name + '\'' +
                ", quota='" + quota + '\'' +
                ", updateFrequency='" + updateFrequency + '\'' +
                ", features=" + features +
                '}';
    }
}
