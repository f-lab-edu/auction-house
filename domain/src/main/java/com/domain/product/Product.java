package com.domain.product;

import com.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;

@Builder
@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void isAvailableToBid(Long bidAmount) {
        if(this.minimumAmount >= bidAmount ) {
            throw new RuntimeException("cannot bid cause bidAmount is less than minimumAmount");
        }
        if(this.bidAmount != null && this.bidAmount > bidAmount) {
            throw new RuntimeException("cannot bid cause bidAmount is less than bidAmount");
        }
    }
}
