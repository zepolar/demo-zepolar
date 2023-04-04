package com.zepolar.demo.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Setter
@Getter
public class TransactionRequest {
    Double amount;
    String sourceNumber;
    String sourceCurrency;
    String destinationNumber;
    String destinationCurrency;
    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}