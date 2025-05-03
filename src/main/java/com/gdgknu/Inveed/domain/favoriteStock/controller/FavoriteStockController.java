package com.gdgknu.Inveed.domain.favoriteStock.controller;

import com.gdgknu.Inveed.domain.favoriteStock.dto.FavoriteStockReqDTO;
import com.gdgknu.Inveed.domain.favoriteStock.dto.FavoriteStockResDTO;
import com.gdgknu.Inveed.domain.favoriteStock.service.FavoriteStockService;
import com.gdgknu.Inveed.response.ResponseUtil;
import com.gdgknu.Inveed.response.SuccessCode;
import com.gdgknu.Inveed.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "FavoriteStock", description = "APIs for favorite stocks")
@RequestMapping("/api/favorite")
public class FavoriteStockController {

    private final FavoriteStockService favoriteStockService;

    @PostMapping
    @Operation(summary = "Create a new favorite stock")
    public ResponseEntity<SuccessResponse<FavoriteStockResDTO>> create(
            @RequestBody @Valid FavoriteStockReqDTO reqDTO, @RequestAttribute("userId") Long userId) {
        FavoriteStockResDTO favoriteStockResDTO = favoriteStockService.createFavoriteStock(reqDTO, userId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.FAVORITE_STOCK_POST_SUCCESS, favoriteStockResDTO);
    }

    @GetMapping
    @Operation(summary = "Retrieves favorite stock ids")
    public ResponseEntity<SuccessResponse<List<FavoriteStockResDTO>>> getFavoriteStocks(
            @RequestAttribute("user_id") Long userId) {
        List<FavoriteStockResDTO> favoriteStockResDTOList = favoriteStockService.getFavoriteStocks(userId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.FAVORITE_STOCK_READ_SUCCESS, favoriteStockResDTOList);
    }

    @DeleteMapping("/{stockCode}")
    @Operation(summary = "Delete the existing favorite stock")
    public ResponseEntity<SuccessResponse<Object>> delete(
            @PathVariable String stockCode, @RequestAttribute("userId") Long userId) {
        favoriteStockService.deleteFavoriteStock(stockCode, userId);
        return ResponseUtil.buildSuccessResponse(SuccessCode.FAVORITE_STOCK_DELETE_SUCCESS);
    }
}
