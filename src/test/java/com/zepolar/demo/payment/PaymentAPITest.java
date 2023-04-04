package com.zepolar.demo.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zepolar.demo.payment.exception.PaymentBodyException;
import com.zepolar.demo.payment.exception.PaymentServerError;
import com.zepolar.demo.payment.request.*;
import com.zepolar.demo.payment.response.PaymentResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentAPITest {
    public static MockWebServer mockBackEnd;
    private ObjectMapper MAPPER = new ObjectMapper();
    private PaymentAPI paymentAPI;

    @BeforeAll
    static void setUp( ) throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown( ) throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize( ) {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        paymentAPI = new PaymentAPI(baseUrl);
    }

    @Test
    @DisplayName("Should return a PaymentBodyException when receive a 4XX code")
    void shouldReturnPaymentBodyException( ) {

        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json")
        );
        PaymentRequest paymentRequest = generate();

        assertThrows(PaymentBodyException.class, () -> {
            paymentAPI.createPayment(paymentRequest);
        });
    }

    @Test
    @DisplayName("Should return a PaymentServerError when receive a 5XX code")
    void shouldReturnPaymentServerError( ) {

        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "application/json")
        );
        PaymentRequest paymentRequest = generate();

        assertThrows(PaymentServerError.class, () -> {
            paymentAPI.createPayment(paymentRequest);
        });
    }

    @Test
    @DisplayName("Should return a PaymentResponse when receive a 200 code")
    void shouldReturnPaymentResponse() {
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("""
                        {
                            "requestInfo": {
                                "status": "Processing"
                            },
                            "paymentInfo": {
                                "amount": 1000,
                                "id": "1924977d-bdae-4239-980b-582b112fe183"
                            }
                        }
                        """)
                .addHeader("Content-Type", "application/json")
        );
        PaymentRequest paymentRequest = generate();
        PaymentResponse response = paymentAPI.createPayment(paymentRequest);
        assertNotNull(response);
    }

    private PaymentRequest generate(){
        Account accountSource = Account.builder().accountNumber("")
                .routingNumber("")
                .currency("")
                .build();

        Account accountDestination = Account.builder().accountNumber("")
                .routingNumber("")
                .currency("")
                .build();

        SourceInformation sourceInformation = SourceInformation.builder()
                .name("").build();
        Destination destination = Destination.builder()
                .account(accountDestination)
                .name("").build();

        Source source = Source.builder().type("COMPANY").account(accountSource).sourceInformation(sourceInformation).build();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .amount(10d)
                .destination(destination)
                .build();
        return paymentRequest;
    }



}