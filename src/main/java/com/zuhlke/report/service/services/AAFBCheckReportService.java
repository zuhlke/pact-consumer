package com.zuhlke.report.service.services;

import com.zuhlke.report.service.models.Holdings;
import com.zuhlke.report.service.models.Token;
import com.zuhlke.report.service.models.TokenRequest;
import com.zuhlke.report.service.models.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AAFBCheckReportService implements ReportService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Holdings extractReportData(Token token, String url) {
        HttpEntity<Token> request = new HttpEntity<>(token, getDefaultHttpHeaders());
        return restTemplate.postForObject(url, request, Holdings.class);
    }

    @Override
    public Token requestToken(TokenRequest tokenRequest, String url) {
        HttpEntity<TokenRequest> request = new HttpEntity<>(tokenRequest, getDefaultHttpHeaders());
        return restTemplate.postForObject(url, request, Token.class);
    }

    @Override
    public TokenStatus requestTokenStatus(Token token, String url) {
        HttpEntity<Token> request = new HttpEntity<>(token, getDefaultHttpHeaders());
        return restTemplate.postForObject(url, request, TokenStatus.class);
    }
}
