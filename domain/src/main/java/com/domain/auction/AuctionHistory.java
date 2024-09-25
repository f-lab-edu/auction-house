package com.domain.auction;

import com.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Builder
@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionHistory extends BaseEntity {
    @Column(nullable = false)
    private Long bidderId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long bidAmount;

}
