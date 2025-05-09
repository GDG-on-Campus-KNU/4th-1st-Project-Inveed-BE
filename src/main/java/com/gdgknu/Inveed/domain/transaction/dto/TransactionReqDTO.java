package com.gdgknu.Inveed.domain.transaction.dto;

import com.gdgknu.Inveed.domain.transaction.entity.TransactionCategory;
import com.gdgknu.Inveed.domain.transaction.entity.TransactionType;

import java.time.LocalDateTime;

public record TransactionReqDTO(
        TransactionType type,
        TransactionCategory category,
        String storeName,
        Integer amount,
        LocalDateTime transactionDate,
        String transactionName
) {
}
