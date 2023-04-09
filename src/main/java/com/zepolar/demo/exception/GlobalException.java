package com.zepolar.demo.exception;

import com.zepolar.demo.payment.exception.PaymentBodyException;
import com.zepolar.demo.payment.exception.PaymentServerError;
import com.zepolar.demo.service.exception.AccountNotFoundException;
import com.zepolar.demo.wallet.exception.WalletBodyException;
import com.zepolar.demo.wallet.exception.WalletException;
import com.zepolar.demo.wallet.exception.WalletInternalException;
import com.zepolar.demo.wallet.exception.WalletNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({WalletException.class})
    public ResponseEntity<Object> handleWalletExceptionWhenNoMoney(WalletBodyException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "No money to continue with the operation");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccountNotFoundException.class, WalletNotFoundException.class})
    public ResponseEntity<Object> handleAccountAndWalletNotFoundException(RuntimeException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "No account was found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PaymentBodyException.class, WalletBodyException.class})
    public ResponseEntity<Object> handleBodyException(RuntimeException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Something is wrong with the request");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PaymentServerError.class, WalletInternalException.class})
    public ResponseEntity<Object> handleInternalServerException(RuntimeException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Something is wrong with the server");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
