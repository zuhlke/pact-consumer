package com.zuhlke.report.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TokenStatus {
    @JsonProperty("status") private String status;

    public TokenStatus() {
    }

    public TokenStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenStatus that = (TokenStatus) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

}
