package com.zepolar.demo.payment;

import com.zepolar.demo.payment.exception.PaymentBodyException;
import com.zepolar.demo.payment.exception.PaymentServerError;
import com.zepolar.demo.payment.request.PaymentRequest;
import com.zepolar.demo.payment.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class PaymentAPI {

    private final Logger logger = Logger.getLogger(PaymentAPI.class.getName());

    private final WebClient webClient;

    @Autowired
    public PaymentAPI(WebClient.Builder builder, Environment env) {
        String baseURL = env.getProperty("payment.api");
        logger.info("Creating PaymentAPI with base "+baseURL);
        this.webClient = builder.baseUrl(baseURL).build();
    }


    public PaymentAPI(String baseURL){
        logger.info("Creating PaymentAPI via BaseURL");
        this.webClient = WebClient.create(baseURL);
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        logger.info("Generate payment "+ paymentRequest.toString());
        return webClient.post()
                .uri("/payments")
                .body(Mono.just(paymentRequest), PaymentRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    logger.warning("Error 4XX to " + paymentRequest.toString());
                    return Mono.error(new PaymentBodyException("Invalid Body"));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    logger.warning("Error 5XX to " + paymentRequest.toString());
                    return Mono.error(new PaymentServerError("The transaction was declined by server"));
                })
                .bodyToMono(PaymentResponse.class)
                .block();
    }
}
