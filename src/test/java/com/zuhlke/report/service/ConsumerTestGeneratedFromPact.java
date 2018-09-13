package com.zuhlke.report.service;

import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.PactReader;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import com.zuhlke.report.service.models.FOFReport;
import com.zuhlke.report.service.models.Token;
import com.zuhlke.report.service.models.TokenRequest;
import com.zuhlke.report.service.models.TokenStatus;
import com.zuhlke.report.service.services.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsumerTestGeneratedFromPact {
    final private MockProviderConfig config = MockProviderConfig.createDefault(PactSpecVersion.V3);

    @Autowired
    private ReportService reportService;

    @Test
    public void runTests() throws MalformedURLException {
//        RequestResponsePact pact = (RequestResponsePact) PactReader.loadPact("./target/pacts/report_service-ihub_mart.json");
        RequestResponsePact pact = (RequestResponsePact) PactReader.loadPact(new URL("http://localhost/pacts/provider/ihub_mart/consumer/report_service/latest"));

        runConsumerTest(pact, config, mockServer -> {
            String url = mockServer.getUrl();
            verifyToken(url);
            verifyTokenStatus(url);
            verifyFOFReport(url);
        });
    }

    private void verifyToken(String url) {
        Token expectedResponse = new Token("unique_token");
        Token actualResponse = reportService.requestToken(new TokenRequest("12345", "fund_id_type", "01/01/2018"), url + "/fnv-api/V1/holdings");

        assertEquals(expectedResponse, actualResponse);
    }

    private void verifyTokenStatus(String url) {
        TokenStatus expectedResponse = new TokenStatus("DONE");
        TokenStatus actualResponse = reportService.requestTokenStatus(new Token("unique_token"), url + "/fnv-api/V1/status");

        assertEquals(expectedResponse, actualResponse);
    }

    private void verifyFOFReport(String url) {
        FOFReport expectedResponse = new FOFReport("000000000410042", "22/05/2018", "485", "FOF Advisory Waiver", "ME", "163.19");
        FOFReport actualResponse = (FOFReport) reportService.extractReportData(new Token("unique_token"), url + "/fnv-api/V1/holdings-data");

        assertEquals(expectedResponse, actualResponse);
    }
}
