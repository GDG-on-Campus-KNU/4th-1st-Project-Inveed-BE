package com.gdgknu.Inveed.domain.kinvest.controller;

import com.gdgknu.Inveed.domain.kinvest.dto.KInvestTradeReqDTO;
import com.gdgknu.Inveed.domain.kinvest.dto.KInvestTradeResDTO;
import com.gdgknu.Inveed.domain.kinvest.service.KInvestService;
import com.gdgknu.Inveed.response.ResponseUtil;
import com.gdgknu.Inveed.response.SuccessCode;
import com.gdgknu.Inveed.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/KInvest")
public class KInvestController {

    private final KInvestService kInvestService;

    @GetMapping("/trade-vol")
    public ResponseEntity<SuccessResponse<List<KInvestTradeResDTO>>> getTradeVol(@RequestBody KInvestTradeReqDTO kInvestTradeReqDTO) {
        List<KInvestTradeResDTO> kInvestTradeResDTO = kInvestService.getTradeVol(kInvestTradeReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.K_INVEST_TRADE_VOL_SUCCESS, kInvestTradeResDTO);
    }

    @GetMapping("/trade-pbmn")
    public ResponseEntity<SuccessResponse<List<KInvestTradeResDTO>>> getTradePbmn(@RequestBody KInvestTradeReqDTO kInvestTradeReqDTO) {
        List<KInvestTradeResDTO> kInvestTradeResDTO = kInvestService.getTradePbmn(kInvestTradeReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.K_INVEST_TRADE_PBMN_SUCCESS, kInvestTradeResDTO);
    }

    @GetMapping("/trade-growth")
    public ResponseEntity<SuccessResponse<List<KInvestTradeResDTO>>> getTradeGrowth(@RequestBody KInvestTradeReqDTO kInvestTradeReqDTO) {
        List<KInvestTradeResDTO> kInvestTradeResDTO = kInvestService.getTradeGrowth(kInvestTradeReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.K_INVEST_TRADE_GROWTH_SUCCESS, kInvestTradeResDTO);
    }

    @GetMapping("/trade-turnover")
    public ResponseEntity<SuccessResponse<List<KInvestTradeResDTO>>> getTradeTurnover(@RequestBody KInvestTradeReqDTO kInvestTradeReqDTO) {
        List<KInvestTradeResDTO> kInvestTradeResDTO = kInvestService.getTradeTurnover(kInvestTradeReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.K_INVEST_TRADE_TRUNOVER_SUCCESS, kInvestTradeResDTO);
    }

}
