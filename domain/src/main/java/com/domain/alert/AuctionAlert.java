package com.domain.alert;

import com.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionAlert extends BaseEntity {
    @Column(nullable = false)
    private Long receiverId;
    @Column(nullable = false)
    private Long auctionHistoryId;
    @Enumerated
    private AlertType alertType;

}
