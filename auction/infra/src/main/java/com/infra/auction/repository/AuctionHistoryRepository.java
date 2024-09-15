package com.infra.auction.repository;

import com.domain.auction.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
}
