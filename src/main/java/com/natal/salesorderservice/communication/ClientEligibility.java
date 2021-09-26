package com.natal.salesorderservice.communication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientEligibility {

    @JsonProperty("isEligible")
    boolean isEligible;

    @JsonProperty("reason")
    String reason;

    public ClientEligibility(boolean isEligible, String reason) {
        this.isEligible = isEligible;
        this.reason = reason;
    }

    public boolean isEligible() {
        return isEligible;
    }

    public String getReason() {
        return reason;
    }
}
