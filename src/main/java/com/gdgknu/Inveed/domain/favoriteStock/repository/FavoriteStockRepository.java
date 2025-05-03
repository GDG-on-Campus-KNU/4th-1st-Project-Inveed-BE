package com.gdgknu.Inveed.domain.favoriteStock.repository;

import com.gdgknu.Inveed.domain.favoriteStock.entity.FavoriteStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteStockRepository extends JpaRepository<FavoriteStock, Long> {
    List<FavoriteStock> findByUserId(Long userId);
    Optional<FavoriteStock> findByStockCodeAndUserId(String stockCode, Long userId);
}
