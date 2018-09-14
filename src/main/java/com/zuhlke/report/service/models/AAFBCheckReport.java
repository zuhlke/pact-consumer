package com.zuhlke.report.service.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class AAFBCheckReport implements Serializable {
    @JsonProperty("accountNumber") private String accountNumber;
    @JsonProperty("asOfDate") private String asOfDate;
    @JsonProperty("securityUniqueQual") private String securityUniqueQual;
    @JsonProperty("securityDescriptionShort") private String securityDescriptionShort;
    @JsonProperty("assetGroup") private String assetGroup;
    @JsonProperty("earnedIncomeLocal") private String earnedIncomeLocal;

    public AAFBCheckReport() {

    }

    public AAFBCheckReport(String accountNumber, String asOfDate, String securityUniqueQual, String securityDescriptionShort, String assetGroup, String earnedIncomeLocal) {
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
        AAFBCheckReport AAFBCheckReport = (AAFBCheckReport) o;
        return Objects.equals(accountNumber, AAFBCheckReport.accountNumber) &&
                Objects.equals(asOfDate, AAFBCheckReport.asOfDate) &&
                Objects.equals(securityUniqueQual, AAFBCheckReport.securityUniqueQual) &&
                Objects.equals(securityDescriptionShort, AAFBCheckReport.securityDescriptionShort) &&
                Objects.equals(assetGroup, AAFBCheckReport.assetGroup) &&
                Objects.equals(earnedIncomeLocal, AAFBCheckReport.earnedIncomeLocal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, asOfDate, securityUniqueQual, securityDescriptionShort, assetGroup, earnedIncomeLocal);
    }
}
