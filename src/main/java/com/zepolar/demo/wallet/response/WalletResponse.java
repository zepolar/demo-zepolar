package com.zepolar.demo.wallet.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class WalletResponse{
    @JsonProperty("wallet_transaction_id")
    private Long walletTransactionId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("user_id")
    private Long userId;
    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}