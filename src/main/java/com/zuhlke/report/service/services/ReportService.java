package com.zuhlke.report.service.services;


import com.zuhlke.report.service.models.Token;
import com.zuhlke.report.service.models.TokenRequest;
import com.zuhlke.report.service.models.TokenStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public interface ReportService {
    Object extractReportData(Token token, String url);
    Token requestToken(TokenRequest tokenRequest, String url);
    TokenStatus requestTokenStatus(Token unique_token, String url);

    default HttpHeaders getDefaultHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

}
