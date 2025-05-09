package com.gdgknu.Inveed.domain.transaction.dto;

import java.time.LocalDateTime;

public record TransactionReqDTO(
        String storeName,
        Integer amount,
        LocalDateTime transactionDate,
        String transactionName
) {
}
