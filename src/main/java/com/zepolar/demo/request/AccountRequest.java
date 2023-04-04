package com.zepolar.demo.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRequest {
    private String name;
    private String surname;
    private String dni;
    private String accountNumber;
    private String routingNumber;
}
