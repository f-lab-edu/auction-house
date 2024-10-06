package com.infra.alert.kafka.dto;

import com.domain.alert.AlertStatus;
import com.infra.common.Message;

public record AuctionAlertResponseMessage(
        Long auctionHistoryId,
        AlertStatus alertStatus
) implements Message {
}
