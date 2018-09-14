package com.zuhlke.report.service.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Holdings implements Serializable {
    @JsonProperty("accountNumber") private String accountNumber;
    @JsonProperty("asOfDate") private String asOfDate;
    @JsonProperty("securityUniqueQual") private String securityUniqueQual;
    @JsonProperty("securityDescriptionShort") private String securityDescriptionShort;
    @JsonProperty("assetGroup") private String assetGroup;
    @JsonProperty("earnedIncomeLocal") private String earnedIncomeLocal;

    public Holdings() {

    }

    public Holdings(String accountNumber, String asOfDate, String securityUniqueQual, String securityDescriptionShort, String assetGroup, String earnedIncomeLocal) {
        this.accountNumber = accountNumber;
        this.asOfDate = asOfDate;
        this.securityUniqueQual = securityUniqueQual;
        this.securityDescriptionShort = securityDescriptionShort;
        this.assetGroup = assetGroup;
        this.earnedIncomeLocal = earnedIncomeLocal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holdings Holdings = (Holdings) o;
        return Objects.equals(accountNumber, Holdings.accountNumber) &&
                Objects.equals(asOfDate, Holdings.asOfDate) &&
                Objects.equals(securityUniqueQual, Holdings.securityUniqueQual) &&
                Objects.equals(securityDescriptionShort, Holdings.securityDescriptionShort) &&
                Objects.equals(assetGroup, Holdings.assetGroup) &&
                Objects.equals(earnedIncomeLocal, Holdings.earnedIncomeLocal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, asOfDate, securityUniqueQual, securityDescriptionShort, assetGroup, earnedIncomeLocal);
    }
}
