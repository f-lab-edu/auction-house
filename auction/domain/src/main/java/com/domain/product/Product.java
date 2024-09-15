package com.domain.product;

import com.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private Long minimumAmount;

    @Column(nullable = false)
    private Instant auctionPeriod;

    @Column
    private Long bidAmount;

    @Column(nullable = false)
    private Long sellerId;

}
