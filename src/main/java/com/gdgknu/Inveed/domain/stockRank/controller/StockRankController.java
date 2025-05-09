package com.gdgknu.Inveed.domain.stockRank.controller;

import com.gdgknu.Inveed.domain.stockRank.dto.StockRankReqDTO;
import com.gdgknu.Inveed.domain.stockRank.dto.StockRankResDTO;
import com.gdgknu.Inveed.domain.stockRank.service.StockRankService;
import com.gdgknu.Inveed.response.ResponseUtil;
import com.gdgknu.Inveed.response.SuccessCode;
import com.gdgknu.Inveed.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "StockRank", description = "APIs for Stock rank")
@RequestMapping("/api/stock-rank")
public class StockRankController {

    private final StockRankService stockRankService;

    @PostMapping("/trade-vol")
    @Operation(summary = "Overseas Stock Ranking by Trading Volume")
    public ResponseEntity<SuccessResponse<List<StockRankResDTO>>> getTradeVol(@RequestBody StockRankReqDTO stockRankReqDTO) {
        List<StockRankResDTO> stockRankResDTO = stockRankService.getTradeVol(stockRankReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.STOCK_RANK_VOL_SUCCESS, stockRankResDTO);
    }

    @PostMapping("/trade-pbmn")
    @Operation(summary = "Overseas Stock Ranking by Trading Value")
    public ResponseEntity<SuccessResponse<List<StockRankResDTO>>> getTradePbmn(@RequestBody StockRankReqDTO stockRankReqDTO) {
        List<StockRankResDTO> stockRankResDTO = stockRankService.getTradePbmn(stockRankReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.STOCK_RANK_PBMN_SUCCESS, stockRankResDTO);
    }

    @PostMapping("/trade-growth")
    @Operation(summary = "Overseas Stock Ranking by Trading Volume Growth Rate")
    public ResponseEntity<SuccessResponse<List<StockRankResDTO>>> getTradeGrowth(@RequestBody StockRankReqDTO stockRankReqDTO) {
        List<StockRankResDTO> stockRankResDTO = stockRankService.getTradeGrowth(stockRankReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.STOCK_RANK_GROWTH_SUCCESS, stockRankResDTO);
    }

    @PostMapping("/trade-turnover")
    @Operation(summary = "Overseas Stock Ranking by Turnover Rate (Popularity)")
    public ResponseEntity<SuccessResponse<List<StockRankResDTO>>> getTradeTurnover(@RequestBody StockRankReqDTO stockRankReqDTO) {
        List<StockRankResDTO> stockRankResDTO = stockRankService.getTradeTurnover(stockRankReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.STOCK_RANK_TRUNOVER_SUCCESS, stockRankResDTO);
    }

}
