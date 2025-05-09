package com.gdgknu.Inveed.domain.transaction.entity;

import com.gdgknu.Inveed.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private String transactionName;

    @Builder
    public Transaction(User user, String storeName, Integer amount, LocalDateTime transactionDate, String transactionName) {
        this.user = user;
        this.storeName = storeName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionName = transactionName;
    }

    public void update(String storeName, Integer amount, LocalDateTime transactionDate, String transactionName) {
        this.storeName = storeName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionName = transactionName;
    }

}
