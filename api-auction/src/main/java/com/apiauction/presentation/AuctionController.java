package com.apiauction.presentation;


import com.apiauction.presentation.dto.BidProductRequestDto;
import com.apiauction.service.AuctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/auction")
    public ResponseEntity<Void> bid(
            @Valid @RequestBody BidProductRequestDto request
    ) {
        auctionService.bid(request.toUsecase());
        return ResponseEntity.ok().build();
    }


}
