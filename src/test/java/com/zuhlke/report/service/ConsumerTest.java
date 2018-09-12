package com.zuhlke.report.service;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.zuhlke.report.service.services.ReportService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
//        headers.put("Content-type", "application/json");

        return builder
                .given("Token is valid") // NOTE: Using provider states are optional, you can leave it out
                .uponReceiving("Request to extract data")
                .path("/FOFReport")
                .method("GET")
//                .headers(headers)
                .willRespondWith()
                .status(200)
//                .headers(headers)
                .body("{\"accountNumber\": \"000000000410042\", \"positionDate\": \"22/05/2018\"}")
//                .given("test state 2") // NOTE: Using provider states are optional, you can leave it out
//                .uponReceiving("ExampleJavaConsumerPactTest second test interaction")
//                .method("OPTIONS")
//                .headers(headers)
//                .path("/second")
//                .body("")
//                .willRespondWith()
//                .status(200)
//                .headers(headers)
//                .body("")
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

    public void runTest(MockServer mockServer) {

//        Map expectedResponse = new HashMap();
//        expectedResponse.put("accountNumber", "000000000410042");
//        expectedResponse.put("positionDate", "22/05/2018");
//        expectedResponse.put("securityUniqueQual", "485");
//        expectedResponse.put("securityDescriptionShort", "FOF Advisory Waiver");
//        expectedResponse.put("assetGroup", "ME");
//        expectedResponse.put("earnedIncomeLocal", "163.19");
        String expectedResponse = "{\"accountNumber\":\"000000000410042\",\"positionDate\":\"22/05/2018\"}";
        assertEquals(expectedResponse, reportService.extractReportData("validToken", "http://localhost:" + mockServer.getPort() + "/FOFReport"));
    }
}
