package com.gdgknu.Inveed.domain.favoriteStock.service;

import com.gdgknu.Inveed.domain.favoriteStock.dto.FavoriteStockReqDTO;
import com.gdgknu.Inveed.domain.favoriteStock.dto.FavoriteStockResDTO;
import com.gdgknu.Inveed.domain.favoriteStock.entity.FavoriteStock;
import com.gdgknu.Inveed.domain.favoriteStock.repository.FavoriteStockRepository;
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
@Transactional(readOnly = true)
public class FavoriteStockService {

    private final FavoriteStockRepository favoriteStockRepository;
    private final UserRepository userRepository;

    public FavoriteStockResDTO createFavoriteStock(FavoriteStockReqDTO reqDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        FavoriteStock favoriteStock = FavoriteStock.builder()
                .stockCode(reqDTO.symb())
                .user(user)
                .build();

        FavoriteStock saved = favoriteStockRepository.save(favoriteStock);

        return FavoriteStockResDTO.from(saved);
    }

    public List<FavoriteStockResDTO> getFavoriteStocks(Long userId) {
        List<FavoriteStock> favoriteStocks = favoriteStockRepository.findByUserId(userId);
        return favoriteStocks.stream()
                .map(FavoriteStockResDTO::from)
                .toList();
    }

    public void deleteFavoriteStock(String stockCode, Long userId) {
        FavoriteStock favoriteStock = favoriteStockRepository.findByStockCodeAndUserId(stockCode, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAVORITE_STOCK_NOT_FOUND));

        favoriteStockRepository.delete(favoriteStock);
    }
}
