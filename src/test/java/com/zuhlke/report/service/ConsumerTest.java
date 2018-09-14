package com.zuhlke.report.service;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.zuhlke.report.service.models.AAFBCheckReport;
import com.zuhlke.report.service.models.Token;
import com.zuhlke.report.service.models.TokenRequest;
import com.zuhlke.report.service.models.TokenStatus;
import com.zuhlke.report.service.services.ReportService;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsumerTest extends ConsumerPactTestMk2 {

    @Autowired
    private ReportService reportService;

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> postHeaders = new HashMap<>();
        postHeaders.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        postHeaders.put("businessGroup","LEGGNEXENALL");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return builder
                .given("No token")
                .uponReceiving("async request")
                    .path("/holding-service/dataMartPosition")
                    .method("POST")
                    .headers(postHeaders)
                    .body(fromResource("PositionRequest.json"))
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body(fromResource("PositionTokenResponse.json"))
                .given("Status of token has become DONE")
                .uponReceiving("async request")
                    .path("/data-service/data/98CE1A035652106D3859BEFBA3464045845C3849")
                    .method("GET")
                    .headers(headers)
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body(fromResource("PositionResponse.json"))

//                .given("No token")
//                .uponReceiving("Request for a token")
//                    .path("/fnv-api/V1/holdings")
//                    .method("POST")
//                    .headers(headers)
//                    .body("{\"fundid\":\"12345\",\"fundidtype\":\"fund_id_type\",\"todate\":\"01/01/2018\"}")
//                .willRespondWith()
//                    .status(200)
//                    .headers(headers)
//                    .body("{\"token\":\"unique_token\"}")
//                .given("Status of token has become DONE")
//                .uponReceiving("Request to get token status")
//                    .path("/fnv-api/V1/status")
//                    .method("POST")
//                    .headers(headers)
//                    .body("{\"token\":\"unique_token\"}")
//                .willRespondWith()
//                    .status(200)
//                    .headers(headers)
//                    .body("{\"status\":\"DONE\"}")
//                .given("Token status is DONE")
//                .uponReceiving("Request to extract AAFB Check report data")
//                    .path("/fnv-api/V1/holdings-data")
//                    .method("POST")
//                    .headers(headers)
//                    .body("{\"token\":\"unique_token\"}")
//                .willRespondWith()
//                    .status(200)
//                    .headers(headers)
//                    .body("{\"accountNumber\":\"000000000410042\",\"positionDate\":\"22/05/2018\",\"securityUniqueQual\":\"485\",\"securityDescriptionShort\":\"FOF Advisory Waiver\",\"assetGroup\":\"ME\",\"earnedIncomeLocal\":\"163.19\"}", ContentType.APPLICATION_JSON)
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
        int mockServerPort = mockServer.getPort();
        verifyToken(mockServerPort);
        verifyAAFBReport(mockServerPort);
        verifyTokenStatus(mockServerPort);
    }

    private void verifyToken(int port) {
        Token expectedResponse = new Token("unique_token");
        Token actualResponse = reportService.requestToken(new TokenRequest("12345", "fund_id_type", "01/01/2018"), "http://localhost:" + port + "/fnv-api/V1/holdings");

        assertEquals(expectedResponse, actualResponse);
    }

    private void verifyTokenStatus(int port) {
        TokenStatus expectedResponse = new TokenStatus("DONE");
        TokenStatus actualResponse = reportService.requestTokenStatus(new Token("unique_token"), "http://localhost:" + port + "/fnv-api/V1/status");

        assertEquals(expectedResponse, actualResponse);
    }

    private void verifyAAFBReport(int port) {
        AAFBCheckReport expectedResponse = new AAFBCheckReport("000000000410042", "22/05/2018", "485", "FOF Advisory Waiver", "ME", "163.19");
        AAFBCheckReport actualResponse = (AAFBCheckReport) reportService.extractReportData(new Token("unique_token"), "http://localhost:" + port + "/fnv-api/V1/holdings-data");

        assertEquals(expectedResponse, actualResponse);
    }

    public String fromResource(String resourceName)  {
        InputStream resourceAsStream = getClass().getResourceAsStream(resourceName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
