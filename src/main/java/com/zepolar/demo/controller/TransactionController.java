package com.zepolar.demo.controller;

import com.zepolar.demo.entity.Transaction;
import com.zepolar.demo.request.TransactionRequest;
import com.zepolar.demo.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController()
@Api(tags = "Transactions")
public class TransactionController {

    private final Logger logger = Logger.getLogger(TransactionController.class.getName());

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Execute operations of transactions", notes = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Something bad happened")
    })
    @PostMapping("/transactions")
    public ResponseEntity<?> update(@RequestBody TransactionRequest transactionRequest){
            this.transactionService.applyChanges(transactionRequest);
            return ResponseEntity.ok("Transaction is ok");
    }

    @GetMapping(path = "/transactions")
    public ResponseEntity<Map<String, Object>> retrieveAllTransactions(
            @RequestParam(required = false) Date createdAt,
            @RequestParam(required = false) Double amount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
            ) {
        try{
            List<Transaction> transactionList = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Transaction> transactionPage = transactionService.retrieve(amount, createdAt, paging);
            transactionList = transactionPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("transactions", transactionList);
            response.put("currentPage", transactionPage.getNumber());
            response.put("totalItems", transactionPage.getTotalElements());
            response.put("totalPages", transactionPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.log(Level.FINER, e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
