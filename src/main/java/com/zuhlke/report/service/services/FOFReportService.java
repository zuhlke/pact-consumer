package com.zuhlke.report.service.services;

import com.zuhlke.report.service.models.FOFReport;
import com.zuhlke.report.service.models.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class FOFReportService implements ReportService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public FOFReport extractReportData(String token, String url) {
        Token tokenRequest = new Token(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Token> request = new HttpEntity<>(tokenRequest, headers);
        return restTemplate.postForObject(url, request, FOFReport.class);
    }
}
