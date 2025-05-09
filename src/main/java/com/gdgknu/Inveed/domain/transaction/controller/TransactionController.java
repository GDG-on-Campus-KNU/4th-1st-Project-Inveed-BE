package com.gdgknu.Inveed.domain.transaction.controller;

import com.gdgknu.Inveed.domain.transaction.dto.TransactionReqDTO;
import com.gdgknu.Inveed.domain.transaction.dto.TransactionResDTO;
import com.gdgknu.Inveed.domain.transaction.service.TransactionService;
import com.gdgknu.Inveed.response.ResponseUtil;
import com.gdgknu.Inveed.response.SuccessCode;
import com.gdgknu.Inveed.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    //사진찍고 받은 정보 저장
    //생성
    @PostMapping
    public ResponseEntity<SuccessResponse<TransactionResDTO>> createTransaction(@RequestBody TransactionReqDTO reqDTO, @RequestAttribute("userId") Long userId) {
        TransactionResDTO transaction = transactionService.createTransaction(reqDTO, userId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.TRANSACTION_POST_SUCCESS, transaction);
    }

    //거래 내역 전체 조회
    @GetMapping
    @Operation
    public ResponseEntity<SuccessResponse<List<TransactionResDTO>>> getTransactions(@RequestAttribute("userId") Long userId) {
        List<TransactionResDTO> transactions = transactionService.getTransactions(userId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.TRANSACTION_READ_SUCCESS, transactions);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<SuccessResponse<TransactionResDTO>> getTransaction(@PathVariable Long transactionId) {
        TransactionResDTO transaction = transactionService.getTransaction(transactionId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.TRANSACTION_READ_SUCCESS, transaction);
    }

    //거래 내역 수정
    @PutMapping("/{transactionId}")
    public ResponseEntity<SuccessResponse<TransactionResDTO>> updateTransaction(@RequestBody TransactionReqDTO reqDto, @PathVariable Long transactionId) {
        TransactionResDTO transaction = transactionService.updateTransaction(reqDto, transactionId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.TRANSACTION_UPDATE_SUCCESS, transaction);
    }

    //거래 내역 삭제
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<SuccessResponse<Object>> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.TRANSACTION_DELETE_SUCCESS);
    }


}
