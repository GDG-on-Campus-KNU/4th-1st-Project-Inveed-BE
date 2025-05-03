package com.gdgknu.Inveed.domain.favoriteStock.entity;

import com.gdgknu.Inveed.domain.BaseTimeEntity;
import com.gdgknu.Inveed.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class FavoriteStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stockCode;

    @CreatedDate
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public FavoriteStock(String stockCode, User user) {
        this.stockCode = stockCode;
        this.user = user;
    }
}
