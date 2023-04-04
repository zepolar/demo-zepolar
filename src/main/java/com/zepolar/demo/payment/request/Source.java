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
public class Source {
    @JsonProperty("type")
    String type;
    @JsonProperty("sourceInformation")
    SourceInformation sourceInformation;
    @JsonProperty("account")
    Account account;

    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}



