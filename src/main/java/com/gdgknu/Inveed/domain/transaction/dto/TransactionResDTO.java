package com.gdgknu.Inveed.domain.transaction.dto;

import com.gdgknu.Inveed.domain.transaction.entity.Transaction;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionResDTO(
        Long transactionId,
        String storeName,
        Integer amount,
        LocalDateTime transactionDate,
        String transactionName
) {

    public static TransactionResDTO from(Transaction transaction) {
        return TransactionResDTO.builder()
                .transactionId(transaction.getId())
                .storeName(transaction.getStoreName())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .transactionName(transaction.getTransactionName())
                .build();
    }
}

