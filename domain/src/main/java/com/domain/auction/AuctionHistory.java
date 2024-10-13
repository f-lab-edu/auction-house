package com.domain.auction;

import com.domain.alert.AlertStatus;
import com.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column
    @Enumerated(EnumType.STRING)
    private AlertStatus isAlertSend;

    public void alertSendSuccess() {
        this.isAlertSend = AlertStatus.SUCCESS;
    }

    public void alertSendFailed() {
        this.isAlertSend = AlertStatus.FAILED;
    }
}
