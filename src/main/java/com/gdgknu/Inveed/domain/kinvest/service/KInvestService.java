package com.gdgknu.Inveed.domain.kinvest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgknu.Inveed.domain.kinvest.dto.KInvestTradeReqDTO;
import com.gdgknu.Inveed.domain.kinvest.dto.KInvestTradeResDTO;
import com.gdgknu.Inveed.response.CustomException;
import com.gdgknu.Inveed.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KInvestService {

    final String KINVEST_BASE_URL = "https://openapi.koreainvestment.com:9443";
    final String KINVEST_PARAMS_URL = "?AUTH&EXCD=NAS&NDAY=0&PRC1=0&PRC2=9999999999&VOL_RANG=0&KEYB";
    final String TRADE_VOL_URL = "/uapi/overseas-stock/v1/ranking/trade-vol";
    final String TRADE_VOL = "HHDFS76310010";
    final String TRADE_PBMN_URL = "/uapi/overseas-stock/v1/ranking/trade-pbmn";
    final String TRADE_PBMN = "HHDFS76320010";
    final String TRADE_GROWTH_URL = "/uapi/overseas-stock/v1/ranking/trade-growth";
    final String TRADE_GROWTH = "HHDFS76330000";
    final String TRADE_TURNOVER_URL = "/uapi/overseas-stock/v1/ranking/trade-turnover";
    final String TRADE_TURNOVER = "HHDFS76340000";

    public List<KInvestTradeResDTO> getTradeVol(KInvestTradeReqDTO kInvestTradeReqDTO) {
        return getKInvestTradeResDTOS(kInvestTradeReqDTO, TRADE_VOL_URL, TRADE_VOL);
    }

    public List<KInvestTradeResDTO> getTradePbmn(KInvestTradeReqDTO kInvestTradeReqDTO) {
        return getKInvestTradeResDTOS(kInvestTradeReqDTO, TRADE_PBMN_URL, TRADE_PBMN);
    }

    public List<KInvestTradeResDTO> getTradeGrowth(KInvestTradeReqDTO kInvestTradeReqDTO) {
        return getKInvestTradeResDTOS(kInvestTradeReqDTO, TRADE_GROWTH_URL, TRADE_GROWTH);
    }

    public List<KInvestTradeResDTO> getTradeTurnover(KInvestTradeReqDTO kInvestTradeReqDTO) {
        return getKInvestTradeResDTOS(kInvestTradeReqDTO, TRADE_TURNOVER_URL, TRADE_TURNOVER);
    }

    private List<KInvestTradeResDTO> getKInvestTradeResDTOS(KInvestTradeReqDTO kInvestTradeReqDTO, String tradeVolUrl, String tradeVol) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                KINVEST_BASE_URL + tradeVolUrl + KINVEST_PARAMS_URL,
                HttpMethod.GET,
                getResponseEntity(kInvestTradeReqDTO, tradeVol),
                String.class
        );

        if (response.getStatusCode() != HttpStatus.OK)
            throw new CustomException(ErrorCode.KINVEST_SERVER_ERROR);

        return parseKInvestTradeRes(response.getBody());
    }

    private HttpEntity<Void> getResponseEntity(KInvestTradeReqDTO kInvestTradeReqDTO, String trId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json; charset=utf-8");
        headers.set("authorization", "Bearer " + kInvestTradeReqDTO.kInvestAccessToken());
        headers.set("appkey", kInvestTradeReqDTO.appKey());
        headers.set("appsecret", kInvestTradeReqDTO.appSecret());
        headers.set("tr_id", trId);
        headers.set("custtype", "P");

        return new HttpEntity<>(headers);
    }


    private List<KInvestTradeResDTO> parseKInvestTradeRes(String body) {
        List<KInvestTradeResDTO> results = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(body);
            JsonNode output2Node = rootNode.path("output2");

            if (!output2Node.isMissingNode() && output2Node.isArray()) {
                for (JsonNode stockNode : output2Node) {
                    if (stockNode.isObject())
                        results.add(KInvestTradeResDTO.fromJsonNode(stockNode));
                }
            } else {
                throw new CustomException(ErrorCode.KINVEST_SERVER_ERROR);
            }
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.KINVEST_SERVER_ERROR);
        }

        return results;
    }


}
