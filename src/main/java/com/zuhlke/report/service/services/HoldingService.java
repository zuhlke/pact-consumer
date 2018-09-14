package com.zuhlke.report.service.services;

import com.zuhlke.report.service.models.Token;
import com.zuhlke.report.service.models.TokenRequest;
import com.zuhlke.report.service.models.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class HoldingService implements ReportService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object extractReportData(Token token, String url) {
        return null;
    }

    @Override
    public Token requestToken(TokenRequest tokenRequest, String url) {
        return null;
    }

    @Override
    public TokenStatus requestTokenStatus(Token unique_token, String url) {
        return null;
    }
}
