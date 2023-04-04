package com.zepolar.demo.wallet;

import com.zepolar.demo.wallet.exception.WalletBodyException;
import com.zepolar.demo.wallet.exception.WalletInternalException;
import com.zepolar.demo.wallet.exception.WalletNotFoundException;
import com.zepolar.demo.wallet.request.WalletRequest;
import com.zepolar.demo.wallet.response.BalanceResponse;
import com.zepolar.demo.wallet.response.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class WalletAPI {

    Logger logger = LoggerFactory.getLogger(WalletAPI.class);
    private final WebClient webClient;

    @Autowired
    public WalletAPI(WebClient.Builder builder, Environment environment) {
        String baseURL = environment.getProperty("wallet.api");
        logger.info("Creating WalletAPI with baseURL "+baseURL);
        this.webClient = builder.baseUrl(baseURL).build();
    }

    public WalletAPI(String baseURL){
        logger.info("Creating WalletAPI via BaseURL");
        this.webClient = WebClient.create(baseURL);
    }

    public WalletResponse createTransaction(WalletRequest walletRequest) {
        logger.info("Generate wallet "+ walletRequest.toString());
        return webClient.post()
                .uri("/transactions")
                .body(Mono.just(walletRequest), WalletRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, response -> {
                    logger.error("Bad Request to "+ walletRequest);
                    return Mono.error(new WalletBodyException("Bad Request"));
                })
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    logger.error("Error 404 to " + walletRequest);
                    return Mono.error(new WalletNotFoundException("Wallet not found"));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    logger.error("Error 500 to " + walletRequest);
                    return Mono.error(new WalletInternalException("Something is wrong"));
                })
                .bodyToMono(WalletResponse.class)
                .block();
    }


    public BalanceResponse retrieveBalance(Long userId){
        logger.info("Retrieve balance to "+userId);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/balance")
                        .queryParam("user_id", userId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BalanceResponse.class)
                .log()
                .block();
    }

}
