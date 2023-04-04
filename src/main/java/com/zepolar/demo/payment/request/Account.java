package com.zepolar.demo.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
@Builder
public class Account {
    @JsonProperty("accountNumber")
    String accountNumber;
    @JsonProperty("currency")
    String currency;
    @JsonProperty("routingNumber")
    String routingNumber;

    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
