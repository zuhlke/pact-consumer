package com.zuhlke.report.service.services;

import com.zuhlke.report.service.domains.FOFReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FOFReportService implements ReportService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String extractReportData(String token, String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }
}
