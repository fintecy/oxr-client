package org.fintecy.md.oxr.model.usage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UsageData {
    private final String appId;
    private final String status;
    private final UserPlan plan;
    private final Usage usage;

    @JsonCreator
    public UsageData(@JsonProperty("app_id") String appId,
                     @JsonProperty("status") String status,
                     @JsonProperty("plan") UserPlan plan,
                     @JsonProperty("usage") Usage usage) {
        this.appId = appId;
        this.status = status;
        this.plan = plan;
        this.usage = usage;
    }

    public String getAppId() {
        return appId;
    }

    public String getStatus() {
        return status;
    }

    public UserPlan getPlan() {
        return plan;
    }

    public Usage getUsage() {
        return usage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsageData)) return false;
        UsageData usageData = (UsageData) o;
        return Objects.equals(appId, usageData.appId) && Objects.equals(status, usageData.status) && Objects.equals(plan, usageData.plan) && Objects.equals(usage, usageData.usage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, status, plan, usage);
    }

    @Override
    public String toString() {
        return "UsageData{" +
                "appId='" + appId + '\'' +
                ", status='" + status + '\'' +
                ", plan=" + plan +
                ", usage=" + usage +
                '}';
    }
}
