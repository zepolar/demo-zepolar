package com.zepolar.demo.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zepolar.demo.wallet.exception.WalletBodyException;
import com.zepolar.demo.wallet.exception.WalletInternalException;
import com.zepolar.demo.wallet.exception.WalletNotFoundException;
import com.zepolar.demo.wallet.request.WalletRequest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WalletAPITest {
    public static MockWebServer mockBackEnd;
    private ObjectMapper MAPPER = new ObjectMapper();
    private WalletAPI walletAPI;

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
        walletAPI = new WalletAPI(baseUrl);
    }

    @Test
    @DisplayName("Should return a WalletBodyException when receive a 400 code")
    void shouldReturnWalletBodyException( ) {

        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(400)
                .addHeader("Content-Type", "application/json")
        );
        WalletRequest walletRequest = generate();

        assertThrows(WalletBodyException.class, () -> {
            walletAPI.createTransaction(walletRequest);
        });
    }


    @Test
    @DisplayName("Should return a WalletNotFoundException when receive a 404 code")
    void shouldReturnWalletNotFoundException( ) {

        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json")
        );
        WalletRequest walletRequest = generate();

        assertThrows(WalletNotFoundException.class, () -> {
            walletAPI.createTransaction(walletRequest);
        });
    }

    @Test
    @DisplayName("Should return a WalletInternalException when receive a 5XX code")
    void shouldReturnWalletInternalException( ) {

        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "application/json")
        );
        WalletRequest walletRequest = generate();


        assertThrows(WalletInternalException.class, () -> {
            walletAPI.createTransaction(walletRequest);
        });
    }

    private WalletRequest generate(){
        WalletRequest walletRequest = WalletRequest.builder()
                .userId(1L)
                .amount(0d)
                .build();

        return walletRequest;
    }

}