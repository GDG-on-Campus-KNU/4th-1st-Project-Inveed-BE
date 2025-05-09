package com.gdgknu.Inveed.domain.transaction.dto;

import com.gdgknu.Inveed.domain.transaction.entity.Transaction;
import com.gdgknu.Inveed.domain.transaction.entity.TransactionCategory;
import com.gdgknu.Inveed.domain.transaction.entity.TransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionResDTO(
        Long transactionId,
        TransactionType type,
        TransactionCategory category,
        String storeName,
        Integer amount,
        LocalDateTime transactionDate,
        String transactionName
) {

    public static TransactionResDTO from(Transaction transaction) {
        return TransactionResDTO.builder()
                .transactionId(transaction.getId())
                .type(transaction.getType())
                .category(transaction.getCategory())
                .storeName(transaction.getStoreName())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .transactionName(transaction.getTransactionName())
                .build();
    }
}

