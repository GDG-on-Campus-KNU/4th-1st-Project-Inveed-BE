package com.gdgknu.Inveed.domain.transaction.service;

import com.gdgknu.Inveed.domain.transaction.dto.TransactionReqDTO;
import com.gdgknu.Inveed.domain.transaction.dto.TransactionResDTO;
import com.gdgknu.Inveed.domain.transaction.entity.Transaction;
import com.gdgknu.Inveed.domain.transaction.repository.TransactionRepository;
import com.gdgknu.Inveed.domain.user.User;
import com.gdgknu.Inveed.domain.user.UserRepository;
import com.gdgknu.Inveed.response.CustomException;
import com.gdgknu.Inveed.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Transactional
    public TransactionResDTO createTransaction(TransactionReqDTO reqDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Transaction transaction = Transaction.builder()
                .user(user)
                .type(reqDTO.type())
                .category(reqDTO.category())
                .storeName(reqDTO.storeName())
                .amount(reqDTO.amount())
                .transactionName(reqDTO.transactionName())
                .transactionDate(reqDTO.transactionDate())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        return TransactionResDTO.from(saved);
    }


    public List<TransactionResDTO> getTransactions(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        return transactions.stream()
                .map(TransactionResDTO::from)
                .toList();
    }

    public TransactionResDTO getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));

        return TransactionResDTO.from(transaction);
    }

    @Transactional
    public TransactionResDTO updateTransaction(TransactionReqDTO reqDTO, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));

        transaction.update(
                reqDTO.type(),
                reqDTO.category(),
                reqDTO.storeName(),
                reqDTO.amount(),
                reqDTO.transactionDate(),
                reqDTO.transactionName()
        );

        return TransactionResDTO.from(transaction);

    }

    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRANSACTION_NOT_FOUND));

        transactionRepository.delete(transaction);
    }

}
