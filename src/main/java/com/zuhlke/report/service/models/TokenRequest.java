package com.zuhlke.report.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TokenRequest {
    @JsonProperty("fundid") private String fundId;
    @JsonProperty("fundidtype") private String fundIdType;
    @JsonProperty("todate") private String toDate;

    public TokenRequest() {
    }

    public TokenRequest(String fundId, String fundIdType, String toDate) {
        this.fundId = fundId;
        this.fundIdType = fundIdType;
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenRequest that = (TokenRequest) o;
        return Objects.equals(fundId, that.fundId) &&
                Objects.equals(fundIdType, that.fundIdType) &&
                Objects.equals(toDate, that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fundId, fundIdType, toDate);
    }

}
