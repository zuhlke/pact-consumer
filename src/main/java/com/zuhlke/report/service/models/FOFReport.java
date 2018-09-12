package com.zuhlke.report.service.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class FOFReport implements Serializable {
    @JsonProperty("accountNumber") private String accountNumber;
    @JsonProperty("positionDate") private String positionDate;
    @JsonProperty("securityUniqueQual") private String securityUniqueQual;
    @JsonProperty("securityDescriptionShort") private String securityDescriptionShort;
    @JsonProperty("assetGroup") private String assetGroup;
    @JsonProperty("earnedIncomeLocal") private String earnedIncomeLocal;

    public FOFReport() {

    }

    public FOFReport(String accountNumber, String positionDate, String securityUniqueQual, String securityDescriptionShort, String assetGroup, String earnedIncomeLocal) {
        this.accountNumber = accountNumber;
        this.positionDate = positionDate;
        this.securityUniqueQual = securityUniqueQual;
        this.securityDescriptionShort = securityDescriptionShort;
        this.assetGroup = assetGroup;
        this.earnedIncomeLocal = earnedIncomeLocal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FOFReport fofReport = (FOFReport) o;
        return Objects.equals(accountNumber, fofReport.accountNumber) &&
                Objects.equals(positionDate, fofReport.positionDate) &&
                Objects.equals(securityUniqueQual, fofReport.securityUniqueQual) &&
                Objects.equals(securityDescriptionShort, fofReport.securityDescriptionShort) &&
                Objects.equals(assetGroup, fofReport.assetGroup) &&
                Objects.equals(earnedIncomeLocal, fofReport.earnedIncomeLocal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, positionDate, securityUniqueQual, securityDescriptionShort, assetGroup, earnedIncomeLocal);
    }
}
