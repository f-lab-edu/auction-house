package com.infra.auction.repository;

import com.domain.auction.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
}
