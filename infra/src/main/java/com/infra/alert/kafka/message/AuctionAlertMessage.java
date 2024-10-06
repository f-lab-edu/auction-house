package com.infra.alert.kafka.message;


import com.domain.alert.AlertType;
import com.domain.alert.AuctionAlert;
import com.infra.common.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionAlertMessage implements Message {
    private Long receiverId;
    private Long auctionHistoryId;
    private AlertType alertType;

    public static AuctionAlertMessage createBidAlertMessage(Long auctionHistoryId, Long receiverId){
        return AuctionAlertMessage.builder()
                .receiverId(receiverId)
                .auctionHistoryId(auctionHistoryId)
                .alertType(AlertType.BID)
                .build();
    }

    public AuctionAlert toEntity() {
        return AuctionAlert
                .builder()
                .receiverId(receiverId)
                .auctionHistoryId(auctionHistoryId)
                .alertType(alertType)
                .build();
    }
}
