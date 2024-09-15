package com.api.presentation.dto;

import com.application.usecase.CreateProductUsecase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record CreateProductRequestDto(
) {
    public record Request(
            @NotBlank(message = "이름이 비어있으면 안됩니다.")
            String name,

            @Positive(message = "최소 입찰 가격은 0보다 커야 합니다.")
            Long minimumAmount,

            @NotNull(message = "경매 기간이 비어있으면 안됩니다.")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            ZonedDateTime auctionPeriod,

            @NotNull(message = "판매자가 비어있으면 안됩니다.")
            Long sellerId
    ) {
        public CreateProductUsecase toUsecase() {
            return CreateProductUsecase
                    .builder()
                    .name(name)
                    .minimumAmount(minimumAmount)
                    .auctionPeriod(Instant.from(auctionPeriod))
                    .sellerId(sellerId)
                    .build();
        }

    }
}
