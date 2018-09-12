package com.zuhlke.report.service;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.zuhlke.report.service.models.FOFReport;
import com.zuhlke.report.service.services.ReportService;
import org.apache.http.entity.ContentType;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsumerTest extends ConsumerPactTestMk2 {

    @Autowired
    private ReportService reportService;

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//        headers.put("charset", "UTF-8");

        return builder
//                .uponReceiving("Request for a token")
//                    .path("/fnv-api/V1/holdings")
//                    .method("POST")
//                    .body("{\"fundid\": \"12345\",\"fundidtype\":fund_id_type,\"todate\":\"01/01/2018\"}")
//                .willRespondWith()
//                    .status(200)
//                    .body("{\"token\":\"unique_token\"}")
//                .uponReceiving("Request to get token status")
//                    .path("/fnv-api/V1/status")
//                    .method("GET")
//                    .body("{\"token\":\"unique_token\"}")
//                .willRespondWith()
//                    .status(200)
//                    .body("{\"status\":\"DONE\"}")
                .given("Token status is DONE") // NOTE: Using provider states are optional, you can leave it out
                .uponReceiving("Request to extract FOF report data")
                    .path("/fnv-api/V1/holdings-data")
                    .method("POST")
                    .headers(headers)
                    .body("{\"token\":\"unique_token\"}")
                .willRespondWith()
                    .status(200)
//                    .headers(headers)
                    .body("{\"accountNumber\":\"000000000410042\",\"positionDate\":\"22/05/2018\",\"securityUniqueQual\":\"485\",\"securityDescriptionShort\":\"FOF Advisory Waiver\",\"assetGroup\":\"ME\",\"earnedIncomeLocal\":\"163.19\"}", ContentType.APPLICATION_JSON)
                .toPact();
    }

    @Override
    protected String providerName() {
        return "ihub_mart";
    }

    @Override
    protected String consumerName() {
        return "report_service";
    }

    @Override
    public void runTest(MockServer mockServer) {
        verifyFOFReport(mockServer.getPort());
    }

    private void verifyFOFReport(int port) {

        FOFReport expectedResponse = new FOFReport("000000000410042", "22/05/2018", "485", "FOF Advisory Waiver", "ME", "163.19");
        FOFReport actualResponse = (FOFReport) reportService.extractReportData("unique_token", "http://localhost:" + port + "/fnv-api/V1/holdings-data");

        assertEquals(expectedResponse, actualResponse);
    }
}
