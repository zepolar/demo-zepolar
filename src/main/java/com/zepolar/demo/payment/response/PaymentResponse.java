package com.zepolar.demo.payment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class PaymentResponse {
    @JsonProperty("requestInfo")
    RequestInfo requestInfo;
    @JsonProperty("paymentInfo")
    PaymentInfo paymentInfo;
    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

@Setter
@Getter
class PaymentInfo {
    @JsonProperty("amount")
    int amount;
    @JsonProperty("id")
    String id;
    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

@Setter
@Getter
class RequestInfo {
    @JsonProperty("status")
    String status;
    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
