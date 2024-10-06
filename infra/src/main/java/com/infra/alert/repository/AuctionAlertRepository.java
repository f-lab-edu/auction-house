package com.infra.alert.repository;

import com.domain.alert.AuctionAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionAlertRepository extends JpaRepository<AuctionAlert, Long> {
}
